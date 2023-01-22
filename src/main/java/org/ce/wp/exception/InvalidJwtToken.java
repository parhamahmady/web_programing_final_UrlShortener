package org.ce.wp.exception;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
public class InvalidJwtToken extends Exception {

    public InvalidJwtToken(String message) {
        super(message);
    }
}
