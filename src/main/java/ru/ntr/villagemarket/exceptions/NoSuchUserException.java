package ru.ntr.villagemarket.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NoSuchUserException extends RuntimeException {

    private final int userId;

    @Override
    public String getMessage() {
        return String.format("Cannot find user with id %s.", userId);
    }
}
