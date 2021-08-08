package ru.ntr.villagemarket.responses;

import lombok.Data;


@Data
public class ErrorResponse extends Response {

    public ErrorResponse(String errorMessage) {
        super(null);
        this.setError(true);
        this.setErrorMessage(errorMessage);
    }
}
