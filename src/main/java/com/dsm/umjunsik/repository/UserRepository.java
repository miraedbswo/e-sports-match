package com.dsm.umjunsik.repository;

import com.dsm.umjunsik.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(String id);
    List<User> getFollowsByIdx(Long idx);
}
