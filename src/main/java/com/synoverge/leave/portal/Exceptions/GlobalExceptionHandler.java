package com.synoverge.leave.portal.Exceptions;

import com.synoverge.leave.portal.DTO.BaseResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
    }

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class,
            BindException.class,
            NoHandlerFoundException.class,
            AsyncRequestTimeoutException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponseEntity> methodArgumentNotValidException(Exception ex) {
        log.error("Entry!! :: GlobalExceptionHandler :: methodArgumentNotValidException");
        BaseResponseEntity baseResponse = null;
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException mex = (MethodArgumentNotValidException) ex;
            mex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            log.error("GlobalExceptionHandler :: methodArgumentNotValidException :: Error => {}", errors);
            baseResponse = new BaseResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Required field is mandatory to be filled...", errors);
        } else if (ex instanceof HttpMessageNotReadableException) {
            HttpMessageNotReadableException hex = (HttpMessageNotReadableException) ex;
            log.error("GlobalExceptionHandler :: HttpMessageNotReadableException :: Error => {}", errors);
            baseResponse = new BaseResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "Input type Miss Matched...", errors);
        }
        log.error("Exit!! :: GlobalExceptionHandler :: methodArgumentNotValidException");
        return new ResponseEntity<BaseResponseEntity>(baseResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoSuchElementException.class, InActiveEmployeeException.class,IllegalArgumentException.class,RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponseEntity> globalServiceException(RuntimeException ex) {
        if(ex instanceof NoSuchElementException) {
            log.error("Entry!! :: GlobalExceptionHandler :: NoSuchElementException");
            BaseResponseEntity baseResponse = badRequestResponse(ex.getMessage());
            log.error("Exit!! :: GlobalExceptionHandler :: NoSuchElementException");
            return new ResponseEntity<BaseResponseEntity>(baseResponse, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof IllegalArgumentException){
            log.error("Entry!! :: GlobalExceptionHandler :: IllegalArgumentException");
            BaseResponseEntity baseResponse = badRequestResponse(ex.getMessage());
            log.error("Exit!! :: GlobalExceptionHandler :: IllegalArgumentException");
            return new ResponseEntity<BaseResponseEntity>(baseResponse, HttpStatus.BAD_REQUEST);
        } else if (ex instanceof InActiveEmployeeException){
            log.error("Entry!! :: GlobalExceptionHandler :: IllegalArgumentException");
            BaseResponseEntity baseResponse = badRequestResponse(ex.getMessage());
            log.error("Exit!! :: GlobalExceptionHandler :: IllegalArgumentException");
            return new ResponseEntity<BaseResponseEntity>(baseResponse, HttpStatus.BAD_REQUEST);
        } else {
            log.error("Entry!! :: GlobalExceptionHandler :: RuntimeException");
            BaseResponseEntity baseResponse = new BaseResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage(), null);
            log.error("Exit!! :: GlobalExceptionHandler :: RuntimeException");
            return new ResponseEntity<BaseResponseEntity>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private BaseResponseEntity badRequestResponse(String message){
        log.error("Entry!! :: GlobalExceptionHandler :: badRequestResponse");
        BaseResponseEntity baseResponse = new BaseResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), message, null);
        log.error("Exit!! :: GlobalExceptionHandler :: badRequestResponse");
        return baseResponse;
    }
}
