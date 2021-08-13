package ru.ntr.villagemarket.exceptions;

public class UserIsBlockedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Account is blocked";
    }
}
