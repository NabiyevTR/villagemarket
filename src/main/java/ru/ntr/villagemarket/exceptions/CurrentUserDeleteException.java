package ru.ntr.villagemarket.exceptions;

import lombok.Getter;

import static ru.ntr.villagemarket.exceptions.ErrorMessages.NO_CURRENT_USER_EXCEPTION;

public class CurrentUserDeleteException extends RuntimeException {
    @Getter
    private final String message = NO_CURRENT_USER_EXCEPTION.toString();
}
