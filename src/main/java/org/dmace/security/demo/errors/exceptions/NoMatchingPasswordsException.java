package org.dmace.security.demo.errors.exceptions;

public class NoMatchingPasswordsException extends RuntimeException {

    public NoMatchingPasswordsException() {
        super("passwords do not match");
    }
}
