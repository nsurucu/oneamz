package com.oneamz.inventory.exception;

public class InvalidDiscountPercentageException extends RuntimeException {

    public InvalidDiscountPercentageException() {
        super("Discount percentage must be between 0 and 100");
    }

    public InvalidDiscountPercentageException(String message) {
        super(message);
    }
}
