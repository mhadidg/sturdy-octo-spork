package me.mhadidg.homework.labforward.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Mustapha Hadid
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  public static final String MISSING_PARAMETERS = "Missing required parameter(s).";
  public static final String INVALID_PARAMETERS = "Invalid parameter(s) value.";

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    RestError error = new RestError(HttpStatus.BAD_REQUEST, MISSING_PARAMETERS, ex.getMessage());
    return makeResponseEntity(error);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {

    RestError error = new RestError(HttpStatus.BAD_REQUEST, INVALID_PARAMETERS, ex.getMessage());
    return makeResponseEntity(error);
  }

  private ResponseEntity<Object> makeResponseEntity(RestError error) {
    return new ResponseEntity<>(error, new HttpHeaders(), error.getStatus());
  }

}