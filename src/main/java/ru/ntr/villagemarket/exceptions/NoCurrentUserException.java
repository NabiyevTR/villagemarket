package ru.ntr.villagemarket.exceptions;

public class NoCurrentUserException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Cannot get current user";
    }
}
