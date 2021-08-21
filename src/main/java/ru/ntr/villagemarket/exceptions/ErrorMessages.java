package ru.ntr.villagemarket.exceptions;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor

@ToString
public enum  ErrorMessages {
    CURRENT_USER_DELETE_EXCEPTION ("Cannot delete yourself. Operation denied"),
    NO_CURRENT_USER_EXCEPTION("Cannot get current user"),
    NO_SUCH_ORDER_EXCEPTION("Cannot find order with id "),
    NO_SUCH_PRODUCT_EXCEPTION("Cannot find order with id "),
    NO_SUCH_USER_EXCEPTION("Cannot find product with id "),
    SUPER_ADMIN_DELETE_EXCEPTION("Cannot delete user with role SUPERADMIN. Operation denied"),
    USER_IS_BLOCKED_EXCEPTION("Account is blocked"),
    USER_WITH_SUCH_EMAIL_EXISTS_EXCEPTION("User with such email exists"),
    USER_WITH_SUCH_PHONE_NUMBER_EXISTS_EXCEPTION("User with such phone number exists"),
    USER_WITH_SUCH_USERNAME_EXISTS_EXCEPTION("User with such username exists");


    private String message;
}
