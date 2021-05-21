package io.swagger.api.exception;

import io.swagger.model.Error;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Error handleUserNotFoundException(final UserNotFoundException ex) {
//        log.error("User not found thrown", ex);
        return new Error("USER_NOT_FOUND", "The user was not found");
    }

    @ExceptionHandler(EmptyBodyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleEmptyBodyException(final EmptyBodyException ex) {
        return new Error("INVALID_BODY", "No values provided.");
    }

    @ExceptionHandler(InvalidAccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleInvalidAccountException(final InvalidAccountException ex) {
        return new Error("INVALID_ACCOUNT", "origin and target accounts cannot be the same.");
    }


    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleInsufficientBalanceException(final InsufficientBalanceException ex) {
        return new Error("INSUFFICIENT_BALANCE", "Account balance is insufficient");
    }

    @ExceptionHandler(InvalidTransactionAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleInvalidTransactionAmountException(final InvalidTransactionAmountException ex) {
        return new Error("INVALID_AMOUNT", "Amount cannot be negative.");
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleAccountNotFoundException(final AccountNotFoundException ex) {
        return new Error("ACCOUNT_NOT_FOUND", "Account with specified IBAN was not found.");
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleTransactionNotFoundException(final TransactionNotFoundException ex) {
        return new Error("TRANSACTION_NOT_FOUND", "This transaction does not exist.");
    }


//    HANDLE VALIDATION ERRORS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {

        String validationError = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String argument = ex.getBindingResult().getAllErrors().get(0).toString();
        return new Error("VALIDATION_ERROR", validationError);

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

    //    CATCH ALL ERRORS
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Error handleThrowable(final Throwable ex) {
        System.out.println("Unexpected error:"+ ex);
        return new Error("INTERNAL_SERVER_ERROR", ex.toString());
    }


}