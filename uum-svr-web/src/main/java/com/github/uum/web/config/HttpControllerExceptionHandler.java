package com.github.uum.web.config;

import com.github.easywork.exception.BaseException;
import com.github.easywork.http.HttpJsonValidationResponse;
import com.github.easywork.http.HttpResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;


@ControllerAdvice()
public class HttpControllerExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(com.github.easywork.support.HttpControllerExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpJsonValidationResponse paramValidExceptionHandler(MethodArgumentNotValidException ex) {
        HttpJsonValidationResponse response = new HttpJsonValidationResponse();
        ex.getBindingResult().getFieldErrors().forEach(e -> response.addValidationError(e.getField(), e.getRejectedValue(), e.getDefaultMessage()));
        return response;
    }

    @ExceptionHandler(ServletException.class)
    public void paramValidExceptionHandler(ServletException ex) {
    }
   /* @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public String paramValidExceptionHandler(HttpMessageNotReadableException ex) {
        return ex.getMessage();
    }*/


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ex.getMessage();
    }


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HttpJsonValidationResponse bindException(BindException ex) {
        HttpJsonValidationResponse response = new HttpJsonValidationResponse();
        ex.getBindingResult().getFieldErrors().forEach(e -> response.addValidationError(e.getField(), e.getRejectedValue(), e.getDefaultMessage()));
        return response;
    }

    //    ClientAbortException.class,
    /*@ExceptionHandler({HttpMediaTypeNotAcceptableException.class,MissingServletRequestParameterException.class,HttpRequestMethodNotSupportedException.class})
    public JsonResponse ignore(Exception ex) {
        return JsonResponse.fail(ex.getMessage());
    }*/
//    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(Exception ex) {
        log.error("server error ", ex);
        return ex.getMessage();
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity baseExceptionHandler(BaseException ex) {
        int httpcode;
        if (ex.getErrors().getCode() < 200) {
            httpcode = HttpResponseCode.失败.code;
        } else {
            httpcode = ex.getErrors().getCode();
        }

        return ResponseEntity.status(httpcode).body(ex.getMessage());
    }
}
