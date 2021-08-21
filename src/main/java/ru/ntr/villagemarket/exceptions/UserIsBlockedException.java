package ru.ntr.villagemarket.exceptions;

import lombok.Getter;
import static ru.ntr.villagemarket.exceptions.ErrorMessages.USER_IS_BLOCKED_EXCEPTION;

public class UserIsBlockedException extends RuntimeException {
    @Getter
    private final String message = USER_IS_BLOCKED_EXCEPTION.toString();
}
