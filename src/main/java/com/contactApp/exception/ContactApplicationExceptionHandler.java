package com.contactApp.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The class for using centralized exception handling whenever exception occured 
 * it should capture and return the exception code.
 * @author Annamalai
 */
@RestControllerAdvice
public class ContactApplicationExceptionHandler {

	@ExceptionHandler(PatternNotMatchingException.class)
	public ResponseEntity<Map<HttpStatus, String>> handleRegexMisMatchException(PatternNotMatchingException exception) {
		Map<HttpStatus, String> map = new HashMap<>();
		map.put(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());

		return new ResponseEntity<Map<HttpStatus, String>>(map,HttpStatus.NOT_ACCEPTABLE);

	}

}
