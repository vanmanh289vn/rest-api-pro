package com.mtp.restapipro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mtp.restapipro.dto.response.ResponseMessage;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value = {MaxUploadSizeExceededException.class, Exception.class} )
	public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException ex){
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("MTP : file too large!!!"));
	}
}
