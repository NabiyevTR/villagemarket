package ru.ntr.villagemarket.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static ru.ntr.villagemarket.exceptions.ErrorMessages.NO_CURRENT_USER_EXCEPTION;
import static ru.ntr.villagemarket.exceptions.ErrorMessages.NO_SUCH_ORDER_EXCEPTION;

@AllArgsConstructor
public class NoSuchOrderException extends RuntimeException  {

    private int orderId;

    @Getter
    private final String message = NO_SUCH_ORDER_EXCEPTION.toString() + orderId;

}
