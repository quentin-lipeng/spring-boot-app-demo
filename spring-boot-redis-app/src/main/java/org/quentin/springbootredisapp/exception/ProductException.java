package org.quentin.springbootredisapp.exception;

public class ProductException extends WrongOperatingException {
    public static final String PRODUCT_NOT_EXIST = "Product not exist";

    public ProductException(String msg) {
        super(msg);
    }
}
