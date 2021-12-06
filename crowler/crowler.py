import os
import threading
from datetime import datetime, timedelta
from typing import Dict, List, Any, Union
from multiprocessing.pool import ThreadPool
from multiprocessing import cpu_count
from multiprocessing import Pool

import pymysql as pymysql
import requests
import re
from bs4 import BeautifulSoup, Tag
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait

year = 2020
month = 7

url_base = 'https://qwer.gg{}'
schedule_url = url_base.format('/schedules/{}-{:0>2}'.format(year, month))

database = {
    'host': os.getenv('DATABASE_URL'),
    'database': os.getenv('DATABASE'),
    'user': os.getenv('DATABASE_USER'),
    'password': os.getenv('DATABASE_PASSWORD'),
    'port': os.getenv('DATABASE_PORT', 3306),
    'cursorclass': pymysql.cursors.DictCursor
}

conn = pymysql.connect(**database)
cursor = conn.cursor(pymysql.cursors.DictCursor)

now: datetime = datetime.utcnow() + timedelta(hours=9)

threadLocal = threading.local()


def get_driver():
    driver = getattr(threadLocal, 'driver', None)
    if driver is None:
        chrome_options = webdriver.ChromeOptions()
        prefs = {'profile.default_content_setting_values': {'cookies': 2, 'images': 2, 'javascript': 2,
                                                            'plugins': 2, 'popups': 2, 'geolocation': 2,
                                                            'notifications': 2, 'auto_select_certificate': 2,
                                                            'fullscreen': 2,
                                                            'mouselock': 2, 'mixed_script': 2, 'media_stream': 2,
                                                            'media_stream_mic': 2, 'media_stream_camera': 2,
                                                            'protocol_handlers': 2,
                                                            'ppapi_broker': 2, 'automatic_downloads': 2,
                                                            'midi_sysex': 2,
                                                            'push_messaging': 2, 'ssl_cert_decisions': 2,
                                                            'metro_switch_to_desktop': 2,
                                                            'protected_media_identifier': 2, 'app_banner': 2,
                                                            'site_engagement': 2,
                                                            'durable_storage': 2}}
        chrome_options.add_experimental_option('prefs', prefs)
        chrome_options.add_argument("start-maximized")
        chrome_options.add_argument("disable-infobars")
        chrome_options.add_argument("--disable-extensions")
        chrome_options.add_argument("--headless")
        driver = webdriver.Chrome(
            options=chrome_options,
            executable_path='./chromedriver'
        )

        setattr(threadLocal, 'driver', driver)
    return driver


class Team:
    def __init__(self, name: str, image: str):
        self.name = name
        self.image = image

    def insert_into_database(self):
        print(f'team created.\
        name: {self.name}, \
        image: {self.image}')

        cursor.execute(
            'INSERT INTO team (name, image) VALUES(%s, %s)',
            (self.name, self.image)
        )
        del self

    @classmethod
    def get_id_by_team_name(cls, name):
        cursor.execute(
            'SELECT id FROM team WHERE name = %s', name
        )
        return cursor.fetchall()


class Highlight:
    """
    Todo: Add videoName, videoImageURL
    """
    def __init__(self, video_url: str, timestamp: str):
        self.video_url = video_url
        self.timestamp = timestamp

    def insert_into_database(self):
        print(f'highlight created.\
        video_url: {self.video_url}, \
        timestamp: {self.timestamp}')

        cursor.execute(
            'INSERT INTO highlight (video_url, timestamp) VALUES(%s, %s)',
            (self.video_url, self.timestamp)
        )
        del self

    @classmethod
    def get_id_by_highlight_video_url(cls, video_url):
        cursor.execute(
            'SELECT id FROM highlight WHERE video_url = %s', video_url
        )
        return cursor.fetchall()


class TeamHighlightMapping:
    def __init__(self, highlight_id: int, team_id: int):
        self.highlight_id = highlight_id
        self.team_id = team_id

    def insert_into_database(self):
        print(f'team_highlight_mapping created.\
        highlight_id: {self.highlight_id}, \
        team_id: {self.team_id}')

        cursor.execute(
            'INSERT INTO team_highlight_mapping (highlight_id, team_id) VALUES(%s, %s)',
            (self.highlight_id, self.team_id)
        )
        del self


class Match:
    def __init__(self, live_url: str, score: str, timestamp: str, red_team_id: int, blue_team_id: int):
        self.live_url = live_url
        self.score = score
        self.timestamp = timestamp
        self.red_team_id = red_team_id
        self.blue_team_id = blue_team_id

    def insert_into_database(self):
        print(f'match created.\
        live_url: {self.live_url}, \
        score: {self.score}, \
        timestamp: {self.timestamp}, \
        red_team_id: {self.red_team_id}, \
        blue_team_id: {self.blue_team_id}')

        cursor.execute(
            'INSERT INTO `match` (live_url, score, timestamp, red_team_id, blue_team_id) VALUES(%s, %s, %s, %s, %s)',
            (self.live_url, self.score, self.timestamp, self.red_team_id, self.blue_team_id)
        )
        del self


def get_schedules():
    req = requests.get(schedule_url)
    html = req.text
    soup = BeautifulSoup(html, 'html.parser')
    result = soup.select(
        '#root > div > div > div.Calendar > div.Calendar__scheduleWrapper > div.Calendar__schedule > div > div > div.Calendar__box__match > a'
    )
    return result


def get_match_data(schedule: Tag) -> Dict[str, Union[str, List[Dict[str, str]]]]:
    regex = re.compile(
        '((?:19|20)\d{2}-(?:0[1-9]|1[012])-(?:0[1-9]|[12][0-9]|3[0-1]))-([a-zA-Z0-9]*)-vs-([a-zA-Z0-9]*)$')
    match_url: str = schedule.get('href')
    match_time: str = schedule.next.contents[0]
    data: List[str] = regex.findall(match_url)[0]

    images = schedule.select('div > img')
    return {
        'match_url': url_base.format(match_url),
        'timestamp': f'{data[0]} {match_time}',
        'teams': [
            {
                'team_name': name,
                'team_image': 'https:{}'.format(image.get('src'))
            } for name, image in zip(data[1:], images)
        ]
    }


def get_match_details(match_url: str, match_date: str) -> Dict[str, Union[str, List[str], None]]:
    highlights: List[str] = []
    req = requests.get(match_url)
    html = req.text
    soup = BeautifulSoup(html, 'html.parser')

    live_url = soup.select_one(
        '#root > div.Match > div > div > a:nth-child(2)'
    )

    score: str = soup.select('#root > div.Match > div > div > div > div')[0].text

    videos = soup.select(
        '#root > div.Match > div > div > div > div.slick-list > div > div > div > div > figure'
    )

    for video in videos:
        style_tag = video.get('style')
        highlight_link = style_tag.replace(
            'background-image:url(https://i.ytimg.com/vi/', ''
        ).replace(
            '/hqdefault.jpg)', ''
        )
        highlights.append(f'https://youtube.com/watch?v={highlight_link}')

    return {
        'score': score,
        'live_url': live_url.get('href') if live_url is not None else None,
        'highlights': highlights
    }


def get_teams_id(teams: List[Dict[str, str]]):
    teams_id = []
    for team in teams:
        team_name = team.get('team_name')

        if len(team_id := Team.get_id_by_team_name(team_name)) == 0:
            Team(team.get('team_name'), team.get('team_image')).insert_into_database()
            team_id = Team.get_id_by_team_name(team_name)

        teams_id.append(team_id[0].get('id'))

    return teams_id


def str_to_datetime(date):
    return datetime.strptime(date, '%Y-%m-%d %H:%M')


def get_highlights_id(highlight_links: List[str], timestamp: str):
    highlights = []

    for link in highlight_links:
        Highlight(
            video_url=link,
            timestamp=timestamp
        ).insert_into_database()
        highlight_id = Highlight.get_id_by_highlight_video_url(link)

        highlights.append(highlight_id[0].get('id'))

    return highlights


def main(schedule):
    match_data = get_match_data(schedule)
    match_date = match_data.get('timestamp')
    match_details = get_match_details(match_data.get('match_url'), match_date)
    teams_id = get_teams_id(match_data.get('teams'))

    Match(
        live_url=match_details.get('live_url'),
        score=match_details.get('score'),
        timestamp=match_date,
        red_team_id=teams_id[0],
        blue_team_id=teams_id[1]
    ).insert_into_database()

    if str_to_datetime(match_data.get('timestamp')) > datetime.now():
        return

    highlights_id = get_highlights_id(match_details.get('highlights'), match_date)
    for highlight_id in highlights_id:
        for team_id in teams_id:
            TeamHighlightMapping(highlight_id, team_id).insert_into_database()


if __name__ == '__main__':
    schedules = get_schedules()

    for schedule in schedules:
        main(schedule)

    # ThreadPool(5).map(main, get_schedules())

    conn.commit()
    cursor.close()
    conn.close()
