package io.swagger.api.exception;

import io.swagger.api.ApiException;
import io.swagger.model.Error;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.security.access.AccessDeniedException;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Error handleUserNotFoundException(final UserNotFoundException ex) {
        return new Error("USER_NOT_FOUND", "The user was not found");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleApiException(final ApiException ex){
        return new Error("BAD_REQUEST", ex.getMessage());
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

    @ExceptionHandler(TransactionLimitError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleTransactionLimitError(final TransactionLimitError ex) {
        return new Error("TRANSACTION_LIMIT_EXCEEDED", ex.getMessage());
    }

    @ExceptionHandler(SavingsAccountMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleSavingsAccountMismatchException(final SavingsAccountMismatchException ex) {
        return new Error("SAVINGS_ACCOUNT_MISMATCH", ex.getMessage());
    }

    @ExceptionHandler({AbsoluteLimitException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleAbsoluteLimitException(final AbsoluteLimitException ex) {
        return new Error("ABSOLUTE_LIMIT_EXCEEDED", ex.getMessage());
    }

    @ExceptionHandler(InvalidTransactionAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleInvalidTransactionAmountException(final InvalidTransactionAmountException ex) {
        return new Error("INVALID_AMOUNT", ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleAccountNotFoundException(final AccountNotFoundException ex) {
        return new Error("ACCOUNT_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleTransactionNotFoundException(final TransactionNotFoundException ex) {
        return new Error("TRANSACTION_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(NoAccessToAccountException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public Error handleNoAccessToAccountException(final NoAccessToAccountException ex) {
        return new Error("NO_PERMISSION", ex.getMessage());
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

        String validationError = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
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