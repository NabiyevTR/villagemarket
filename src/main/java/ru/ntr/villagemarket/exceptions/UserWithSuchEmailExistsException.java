package ru.ntr.villagemarket.exceptions;

import lombok.Getter;
import static ru.ntr.villagemarket.exceptions.ErrorMessages.USER_WITH_SUCH_EMAIL_EXISTS_EXCEPTION;

public class UserWithSuchEmailExistsException extends RuntimeException {
    @Getter
    private final String message =USER_WITH_SUCH_EMAIL_EXISTS_EXCEPTION.toString();
}
