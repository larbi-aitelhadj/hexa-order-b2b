package com.larbi.hexa_order_b2b.domain.exception;

/**
 * Exception thrown to indicate an error or invalid operation in the order domain.
 * This is a runtime exception, typically used for business rule violations or domain-specific errors
 * that occur during the processing of an order.
 */
public class OrderDomainException extends RuntimeException {

    /**
     * Constructs a new {@code OrderDomainException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link Throwable#getMessage()} method)
     */
    public OrderDomainException(String message) {
        super(message);
    }
}


