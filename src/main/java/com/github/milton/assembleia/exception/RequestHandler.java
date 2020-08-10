package com.github.milton.assembleia.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RequestHandler {

	@ExceptionHandler(value = {RequestException.class})
	public ResponseEntity<Object> handleResquestException(RequestException ex){
		
		ApiException apiException = new ApiException(
				ex.getMessage(), 
				HttpStatus.BAD_REQUEST, 
				ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
		
		return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
	}
 	
}
