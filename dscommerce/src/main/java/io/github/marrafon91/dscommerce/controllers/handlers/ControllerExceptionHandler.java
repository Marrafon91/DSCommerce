package io.github.marrafon91.dscommerce.controllers.handlers;

import io.github.marrafon91.dscommerce.dto.CustomError;
import io.github.marrafon91.dscommerce.services.excptions.ResourceNotFoundExecption;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

   @ExceptionHandler(ResourceNotFoundExecption.class)
   public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundExecption e, HttpServletRequest request) {
       HttpStatus status = HttpStatus.NOT_FOUND;
       CustomError err = new CustomError(Instant.now(), status.value() , e.getMessage(), request.getRequestURI());
       return ResponseEntity.status(status).body(err);
   }
}
