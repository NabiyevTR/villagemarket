package ru.ntr.villagemarket.exceptions;

public class UserWithSuchUsernameExistsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "User with such username exists";
    }
}
