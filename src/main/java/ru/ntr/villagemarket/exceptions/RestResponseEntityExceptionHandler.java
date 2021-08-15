package ru.ntr.villagemarket.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ntr.villagemarket.responses.ErrorResponse;
import ru.ntr.villagemarket.responses.Response;

import javax.security.auth.login.AccountLockedException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            UsernameNotFoundException.class,
            AccountLockedException.class,
            BadCredentialsException.class,
            UserWithSuchUsernameExistsException.class,
            UserWithSuchEmailExistsException.class,
            UserWithSuchPhoneNumberExistsException.class,
            NoCurrentUserException.class
    })
    protected ResponseEntity<Object> handleAuthErrors(RuntimeException ex, WebRequest request) {
        Response response = new ErrorResponse(ex.getMessage());
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {
            NoSuchUserException.class,
            SuperAdminDeleteException.class,
            CurrentUserDeleteException.class
    })
    protected ResponseEntity<Object> handleDeleteErrors(RuntimeException ex, WebRequest request) {
        Response response = new ErrorResponse(ex.getMessage());
        return handleExceptionInternal(ex, response,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
