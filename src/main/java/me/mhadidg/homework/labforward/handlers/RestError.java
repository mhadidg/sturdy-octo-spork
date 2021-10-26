package me.mhadidg.homework.labforward.handlers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.List;

/**
 * @author Mustapha Hadid
 */
@Data
@AllArgsConstructor
public class RestError {

  private HttpStatus status;
  private String message;
  private Collection<String> errors;

  public RestError(HttpStatus status, String message, String error) {
    this(status, message, List.of(error));
  }

}