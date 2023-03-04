package com.example.clean.code.controller.advice;

import com.example.clean.code.exception.PostNotFoundException;
import com.example.clean.code.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleGeneralException(Exception ex) {
		log.error("Internal error: ", ex);
		return "Internal server error occurred";
	}


	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNotFoundException(UserNotFoundException ex) {
		log.error("User not found: " + ex.getLocalizedMessage());
		return "User with given id doesn’t exist";
	}

	@ExceptionHandler(PostNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNotFoundException(PostNotFoundException ex) {
		log.error("Post not found: " + ex.getLocalizedMessage());
		return "Post with given id doesn’t exist";
	}
}
