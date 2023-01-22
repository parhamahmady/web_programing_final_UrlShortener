package org.ce.wp.exception;

/**
 * @author Parham Ahmadi
 * @since 20.01.23
 */
public class InvaildJwtToken extends Exception {

    public InvaildJwtToken(String message) {
        super(message);
    }
}
