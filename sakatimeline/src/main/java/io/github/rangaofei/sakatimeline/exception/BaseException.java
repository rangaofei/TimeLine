package io.github.rangaofei.sakatimeline.exception;

public class BaseException extends RuntimeException {
    private int exceptionCode;

    public BaseException(ExceptionMessage message) {
        super(message.getMessage());
        this.exceptionCode = message.getCode();
    }
}
