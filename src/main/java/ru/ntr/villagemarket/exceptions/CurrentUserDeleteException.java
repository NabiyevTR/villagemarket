package ru.ntr.villagemarket.exceptions;

public class CurrentUserDeleteException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Cannot delete yourself. Operation denied";
    }
}
