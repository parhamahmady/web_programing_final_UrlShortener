package org.ce.wp.exception;

import java.io.Serial;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
public class InvalidUrlIdException extends Exception {

    @Serial
    private static final long serialVersionUID = 877369017402029942L;

    public InvalidUrlIdException(String message) {
        super(message);
    }
}
