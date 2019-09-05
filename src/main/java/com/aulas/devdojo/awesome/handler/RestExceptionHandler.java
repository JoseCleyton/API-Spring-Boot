package com.aulas.devdojo.awesome.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.aulas.devdojo.awesome.error.ErrorDetails;
import com.aulas.devdojo.awesome.error.ResourceBadRequestDetails;
import com.aulas.devdojo.awesome.error.ResourceBadRequestException;
import com.aulas.devdojo.awesome.error.ResourceNotFoundDetails;
import com.aulas.devdojo.awesome.error.ResourcesNotFoundException;
import com.aulas.devdojo.awesome.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourcesNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourcesNotFoundException rfnException) {
		ResourceNotFoundDetails rnfDetails = ResourceNotFoundDetails.Builder.newBuilder()
				.withTimestamp(new Date().getTime()).withTitle("Resource not found")
				.withDetail(rfnException.getMessage()).withStatus(HttpStatus.NOT_FOUND.value())
				.withDeveloperMessage(rfnException.getClass().getName()).build();
		return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceBadRequestException.class)
	public ResponseEntity<?> handleResourceBadRequestException(ResourceBadRequestException badRequestException) {
		ResourceBadRequestDetails badRequestDetails = ResourceBadRequestDetails.Builder.newBuilder()
				.withTimestamp(new Date().getTime()).withTitle("Resource bad request")
				.withDetail(badRequestException.getMessage()).withStatus(HttpStatus.BAD_REQUEST.value())
				.withDeveloperMessage(badRequestException.getClass().getName()).build();
		return new ResponseEntity<>(badRequestDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manvException,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> fieldError = manvException.getBindingResult().getFieldErrors();
		String field = fieldError.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldMessage = fieldError.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

		ValidationErrorDetails veDetails = ValidationErrorDetails.Builder.newBuilder()
				.withTimestamp(new Date().getTime()).withTitle("Field validation error")
				.withDetail("Field validation error").withStatus(HttpStatus.BAD_REQUEST.value())
				.withDeveloperMessage(manvException.getClass().getName()).field(field).fieldMessage(fieldMessage)
				.build();
		return new ResponseEntity<>(veDetails, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorDetails errorDetails = ErrorDetails.Builder.newBuilder().withTimestamp(new Date().getTime())
				.withStatus(status.value()).withTitle("Internal Exception").withDetail(ex.getMessage())
				.withDeveloperMessage(ex.getClass().getName()).build();
		return new ResponseEntity<>(errorDetails, headers, status);
	}

}
