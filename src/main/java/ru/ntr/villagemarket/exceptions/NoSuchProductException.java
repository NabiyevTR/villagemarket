package ru.ntr.villagemarket.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.ntr.villagemarket.exceptions.ErrorMessages.NO_SUCH_PRODUCT_EXCEPTION;

@AllArgsConstructor
public class NoSuchProductException extends RuntimeException  {

    private int productId;

    @Getter
    private final String message = NO_SUCH_PRODUCT_EXCEPTION.toString() + productId;


}
