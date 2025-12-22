package io.github.marrafon91.dscommerce.controllers.handlers;

import io.github.marrafon91.dscommerce.dto.CustomError;
import io.github.marrafon91.dscommerce.dto.ValidationError;
import io.github.marrafon91.dscommerce.services.excptions.DatabaseExecption;
import io.github.marrafon91.dscommerce.services.excptions.ForbiddenException;
import io.github.marrafon91.dscommerce.services.excptions.ResourceNotFoundExecption;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<CustomError> forbidden(ForbiddenException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        CustomError err = new CustomError(Instant.now(), status.value() , e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

   @ExceptionHandler(DatabaseExecption.class)
   public ResponseEntity<CustomError> dataBase(DatabaseExecption e, HttpServletRequest request) {
       HttpStatus status = HttpStatus.BAD_REQUEST;
       CustomError err = new CustomError(Instant.now(), status.value() , e.getMessage(), request.getRequestURI());
       return ResponseEntity.status(status).body(err);
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<CustomError> methodArgumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
       HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
       ValidationError err = new ValidationError(Instant.now(), status.value() , "Dados inválidos", request.getRequestURI());
       for (FieldError f : e.getBindingResult().getFieldErrors()) {
          err.addError(f.getField(), f.getDefaultMessage());
       }
       return ResponseEntity.status(status).body(err);
   }
}
