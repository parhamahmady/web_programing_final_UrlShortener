package org.ce.wp.exception;

import java.io.Serial;

/**
 * @author Parham Ahmadi
 * @since 27.06.23
 */
public class ShortenerInternalException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -2162933675166444905L;

    public ShortenerInternalException(String message) {
        super(message);
    }
}
