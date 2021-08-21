package ru.ntr.villagemarket.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.ntr.villagemarket.exceptions.ErrorMessages.NO_SUCH_USER_EXCEPTION;

@AllArgsConstructor
public class NoSuchUserException extends RuntimeException {

    private int userId;

    @Getter
    private final String message = NO_SUCH_USER_EXCEPTION.toString() + userId;
}
