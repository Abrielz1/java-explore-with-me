package ru.practicum.ewm.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(final ObjectNotFoundException e) {
        log.warn("404 {}", e.getMessage(), e);
        return new ErrorResponse("Object not found 404", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerBadRequest(final BadRequestException e) {
        log.warn("404 {}", e.getMessage(), e);
        return new ErrorResponse("Object not available 400 ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleIntegrityException(final ConflictException e) {
        log.warn("409 {}", e.getMessage(), e);
        return new ErrorResponse("No valid data", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleIntegrityException(final DataAccessException e) {
        log.warn("409 {}", e.getMessage(), e);
        return new ErrorResponse("No valid data", e.getMessage());
    }
}

