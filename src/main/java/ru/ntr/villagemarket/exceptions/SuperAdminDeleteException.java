package ru.ntr.villagemarket.exceptions;

import lombok.Getter;
import static ru.ntr.villagemarket.exceptions.ErrorMessages.SUPER_ADMIN_DELETE_EXCEPTION;

public class SuperAdminDeleteException extends RuntimeException {
    @Getter
    private final String message = SUPER_ADMIN_DELETE_EXCEPTION.toString();
}
