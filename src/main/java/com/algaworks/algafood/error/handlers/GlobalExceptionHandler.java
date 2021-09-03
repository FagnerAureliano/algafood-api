package com.algaworks.algafood.error.handlers;

import com.algaworks.algafood.error.models.*;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static Map<Class<?>, HttpStatus> httpStatusMap = new HashMap<>();

    static {
        httpStatusMap.put(AccessDeniedException.class, HttpStatus.FORBIDDEN);
        httpStatusMap.put(ForbiddenAccessException.class, HttpStatus.FORBIDDEN);
        httpStatusMap.put(BusinessException.class, HttpStatus.BAD_REQUEST);
        httpStatusMap.put(ValidationException.class, HttpStatus.BAD_REQUEST);
        httpStatusMap.put(EntityNotFoundException.class, HttpStatus.NOT_FOUND);
        httpStatusMap.put(IntegrationException.class, HttpStatus.SERVICE_UNAVAILABLE);
        httpStatusMap.put(InexistentFieldException.class, HttpStatus.BAD_REQUEST);
        httpStatusMap.put(RepositoryNotImplementedException.class, HttpStatus.FAILED_DEPENDENCY);
        httpStatusMap.put(NoSuchMethodException.class, HttpStatus.FAILED_DEPENDENCY);
        httpStatusMap.put(IllegalAccessException.class, HttpStatus.FAILED_DEPENDENCY);
        httpStatusMap.put(ExternalServerDownException.class, HttpStatus.SERVICE_UNAVAILABLE);
        httpStatusMap.put(InvalidDataAccessApiUsageException.class, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(
        {
            AccessDeniedException.class,
            ForbiddenAccessException.class,
            BusinessException.class,
            ValidationException.class,
            EntityNotFoundException.class,
            IntegrationException.class,
            InexistentFieldException.class,
            RepositoryNotImplementedException.class,
            NoSuchMethodException.class,
            IllegalAccessException.class,
            ExternalServerDownException.class,
            InvalidDataAccessApiUsageException.class,
        }
    )
    public ResponseEntity<Object> handleAccessDeniedException(RuntimeException exception, WebRequest request) {
        ExceptionResponseObject response = new ExceptionResponseObject(exception, request);
        return handleExceptionInternal(exception, response, new HttpHeaders(), httpStatusMap.get(exception.getClass()), request);
    }

    

    private class ExceptionResponseObject implements Serializable {

        private static final long serialVersionUID = 1L;

        public final Instant timestamp;
        public final Integer status;
        public final String error;
        public final String message;
        public final String path;

        public ExceptionResponseObject(RuntimeException exception, WebRequest request) {
            this.timestamp = Instant.now();
            this.status = httpStatusMap.get(exception.getClass()).value();
            this.error = exception.getClass().getSimpleName();
            this.message = exception.getLocalizedMessage();
            this.path = ((ServletWebRequest) request).getRequest().getRequestURI();
        }
    }
}
