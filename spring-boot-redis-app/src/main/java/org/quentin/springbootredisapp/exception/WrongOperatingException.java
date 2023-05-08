package org.quentin.springbootredisapp.exception;

public class WrongOperatingException extends RuntimeException {
    public WrongOperatingException(String msg) {
        super(msg);
    }
}
