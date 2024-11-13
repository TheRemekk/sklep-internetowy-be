package com.example.order.exception;

public class EmptyBasketException extends RuntimeException{
    public EmptyBasketException() {
        super();
    }

    public EmptyBasketException(String message) {
        super(message);
    }

    public EmptyBasketException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyBasketException(Throwable cause) {
        super(cause);
    }
}
