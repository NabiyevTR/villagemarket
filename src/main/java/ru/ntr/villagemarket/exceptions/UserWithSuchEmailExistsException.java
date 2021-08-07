package ru.ntr.villagemarket.exceptions;

public class UserWithSuchEmailExistsException extends RuntimeException {
    @Override
    public String getMessage() {
        return "User with such email exists";
    }
}
