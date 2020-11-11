package com.mybank.app.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;





@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value=AccountInfoNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleAccountInfoNotFoundException(AccountInfoNotFoundException accountInfoNotFoundException){
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setStatusCode("ACCOUNT INFO");
		errorMessage.setStatusMessage(accountInfoNotFoundException.getMessage());
		
		return new ResponseEntity<ErrorMessage>(errorMessage,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value=UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException userNotFoundException){
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setStatusCode("USER ID");
		errorMessage.setStatusMessage(userNotFoundException.getMessage());
		
		return new ResponseEntity<ErrorMessage>(errorMessage,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(value=RecordNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleRecordNotFoundException(RecordNotFoundException recordNotFoundException){
		ErrorMessage errorMessage=new ErrorMessage();
		errorMessage.setStatusCode("TRANSACTIONS");
		errorMessage.setStatusMessage(recordNotFoundException.getMessage());
		
		return new ResponseEntity<ErrorMessage>(errorMessage,HttpStatus.BAD_REQUEST);
		
	}
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException  argInvalidException, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorMessage response = new ErrorMessage();
		response.setStatusCode("SourceId");
		String allFieldErrors = argInvalidException.getBindingResult().getFieldErrors().stream()
				.map(e -> e.getDefaultMessage()).collect(Collectors.joining(", "));
		response.setStatusMessage(allFieldErrors);
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}


}
