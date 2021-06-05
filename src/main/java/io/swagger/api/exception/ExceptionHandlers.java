package io.swagger.api.exception;

import io.swagger.model.Error;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleInvalidRequestException(final InvalidRequestException ex) {
        return new Error("INVALID_REQUEST", ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Error handleUnauthorizedRequestException(final UnauthorizedRequestException ex) {
        return new Error("UNAUTHORIZED", ex.getMessage());
    }

    @ExceptionHandler(RequestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Error handleRequestNotFoundException(final RequestNotFoundException ex) {
        return new Error("NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(TransactionLimitException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleTransactionLimitError(final TransactionLimitException ex) {
        return new Error("TRANSACTION_LIMIT_EXCEEDED", ex.getMessage());
    }

    @ExceptionHandler({AbsoluteLimitException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleAbsoluteLimitException(final AbsoluteLimitException ex) {
        return new Error("ABSOLUTE_LIMIT_EXCEEDED", ex.getMessage());
    }

    @ExceptionHandler(DailyLimitReachedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleDailyLimitReachedException(final DailyLimitReachedException ex) {
        return new Error("TRANSACTION_INVALID", ex.getMessage());
    }

//    HANDLE VALIDATION ERRORS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {

        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
        String error = fieldError.getDefaultMessage();

        if (fieldError.getObjectName() == "createAccountDto" && fieldError.getField() == "accountType") {
            error = "Field accountType must be either 'savings' or 'current'.";
        }

        return new Error("VALIDATION_ERROR", error);
    }

//    HANDLE MISSING JSON PROPERTY
    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleHttpMessageConversionException(final HttpMessageConversionException ex) {
        return new Error("INVALID_REQUEST",ex.getMessage());
    }

    //    HANDLE INVALID URL PARAMETER
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
        return new Error("INVALID_REQUEST", ex.getMessage());
    }

    //    HANDLE NOT FOUND ERRORS
    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleEmptyResultDataAccessException(final EmptyResultDataAccessException ex) {
        return new Error("NOT_FOUND_ERROR", ex.getMessage());
    }

//    HANDLE JSON PARSE ERRORS
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleHttpMessageNotReadableException(final HttpMessageNotReadableException ex) {
        return new Error("INVALID_JSON", ex.getMessage());
    }

//    HANDLE ACCESS DENIED (employee only endpoint reached as user)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Error handleAccessDeniedException(final AccessDeniedException ex) {
        return new Error("ACCESS_DENIED", "You don't have the necessary permissions to access this resource.");
    }

//    HANDLE INVALID REQUEST METHOD
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
        return new Error("INVALID_REQUEST_METHOD", ex.getMessage());
    }

    //    CATCH ALL ERRORS
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Error handleThrowable(final Throwable ex) {
        System.out.println("Unexpected error:"+ ex);
        return new Error("INTERNAL_SERVER_ERROR", ex.toString());
    }
}