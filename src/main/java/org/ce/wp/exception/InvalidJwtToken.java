package org.ce.wp.exception;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
public class InvalidJwtToken extends Exception {

    public InvalidJwtToken(String message) {
        super(message);
    }
}
