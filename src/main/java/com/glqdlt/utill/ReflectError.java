package com.glqdlt.utill;

public class ReflectError extends RuntimeException {
    public ReflectError(String message) {
        super(message);
    }

    public ReflectError(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectError(Throwable cause) {
        super(cause);
    }
}
