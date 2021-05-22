package io.swagger.api;

import io.swagger.model.Error;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

// https://www.baeldung.com/spring-boot-custom-error-page

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<Error> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String errorCode = "";
        String errorMessage = "";
        Integer statusCode = 404;

        if (status != null) {
            statusCode = Integer.valueOf(status.toString());
            System.out.println("STATUS CODE:" + statusCode);

            switch (statusCode) {
                case (401):
                    System.out.println("CASE 401");
                    errorCode = "UNAUTHORIZED";
                    errorMessage = "You need to log in to access this resource.";
                    break;
                case (404):
                    System.out.println("CASE 404");
                    errorCode = "NOT_FOUND";
                    errorMessage = "The requested resource was not found.";
                    break;
            }

        } else {
            errorCode = "ERROR";
            errorMessage = "This is a default error message";
        }

        return new ResponseEntity<Error>(new Error(errorCode,errorMessage), HttpStatus.valueOf(statusCode));
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
