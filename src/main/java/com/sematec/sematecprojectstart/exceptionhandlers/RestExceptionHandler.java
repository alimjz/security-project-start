package com.sematec.sematecprojectstart.exceptionhandlers;


import com.sematec.sematecprojectstart.controller.ErrorDetail;
import com.sematec.sematecprojectstart.controller.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler  {

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request) {
		ValidationError validationError = new ValidationError();
		validationError.setMessage("Resource Does Not Exist.");
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
		errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
		errorDetail.setTitle("Resource Not Found");
		errorDetail.setDetail(rnfe.getMessage());
		errorDetail.setDeveloperMessage(rnfe.getClass().getName());
		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException accessDeniedException){
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
		errorDetail.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorDetail.setTitle("Access Denied.");
		errorDetail.setDetail(accessDeniedException.getLocalizedMessage());
		errorDetail.setDeveloperMessage(accessDeniedException.getClass().getName());
		return new ResponseEntity<>(errorDetail,null,HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(HttpClientErrorException.Unauthorized.class)
	public ResponseEntity<?> handleUnAuthorizedException(HttpClientErrorException.Unauthorized unauthorized){
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
		errorDetail.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorDetail.setTitle("UnAuthorized User try.");
		errorDetail.setDetail(unauthorized.getLocalizedMessage());
		errorDetail.setDeveloperMessage(unauthorized.getClass().getName());
		return new ResponseEntity<>(errorDetail,null,HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<?> handleSecurityException(SecurityException securityException){
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
		errorDetail.setStatus(HttpStatus.EXPECTATION_FAILED.value());
		errorDetail.setTitle("UnAuthorized User try.");
		errorDetail.setDetail(securityException.getLocalizedMessage());
		errorDetail.setDeveloperMessage(securityException.getClass().getName());
		return new ResponseEntity<>(errorDetail,null,HttpStatus.EXPECTATION_FAILED);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException manve,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		// Populate errorDetail instance
		errorDetail.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
		errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
		errorDetail.setTitle("Validation Failed");
		errorDetail.setDetail("Input validation failed");
		errorDetail.setDeveloperMessage(manve.getClass().getName());
		
		// Create ValidationError instances
		List<FieldError> fieldErrors =  manve.getBindingResult().getFieldErrors();
		for(FieldError fe : fieldErrors) {
			
			List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());
			if(validationErrorList == null) {
				validationErrorList = new ArrayList<ValidationError>();
				errorDetail.getErrors().put(fe.getField(), validationErrorList);
			}
			ValidationError validationError = new ValidationError();
			validationError.setCode(fe.getCode());
			validationError.setMessage(messageSource.getMessage(fe, null));
			validationErrorList.add(validationError);
		}
		
		return handleExceptionInternal(manve, errorDetail, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(new Timestamp(System.currentTimeMillis()).toString());
		errorDetail.setStatus(status.value());
		errorDetail.setTitle("Message Not Readable");
		errorDetail.setDetail(ex.getMessage());
		errorDetail.setDeveloperMessage(ex.getClass().getName());
		
		return handleExceptionInternal(ex, errorDetail, headers, status, request);
	}
	
}
