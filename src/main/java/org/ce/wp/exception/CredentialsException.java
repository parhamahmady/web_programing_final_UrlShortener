package org.ce.wp.exception;

import java.io.Serial;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
public class CredentialsException extends Exception {

    @Serial
    private static final long serialVersionUID = -8754008535510475407L;

    public CredentialsException(String message) {
        super(message);
    }
}
