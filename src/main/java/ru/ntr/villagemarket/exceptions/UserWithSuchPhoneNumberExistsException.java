package ru.ntr.villagemarket.exceptions;

import lombok.Getter;
import static ru.ntr.villagemarket.exceptions.ErrorMessages.USER_WITH_SUCH_PHONE_NUMBER_EXISTS_EXCEPTION;

public class UserWithSuchPhoneNumberExistsException extends RuntimeException {
    @Getter
    private final String message = USER_WITH_SUCH_PHONE_NUMBER_EXISTS_EXCEPTION.toString();
}
