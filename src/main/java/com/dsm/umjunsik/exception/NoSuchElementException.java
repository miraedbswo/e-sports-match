package com.dsm.umjunsik.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no such element")
public class NoSuchElementException extends RuntimeException {

}
