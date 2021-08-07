package ru.ntr.villagemarket.exceptions;

public class UserWithSuchPhoneNumberExistsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "User with such phone number exists";
    }
}
