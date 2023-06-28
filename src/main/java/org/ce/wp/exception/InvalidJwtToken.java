package org.ce.wp.exception;

import java.io.Serial;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
public class InvalidJwtToken extends Exception {

    @Serial
    private static final long serialVersionUID = 8992808986630495230L;

    public InvalidJwtToken(String message) {
        super(message);
    }
}
