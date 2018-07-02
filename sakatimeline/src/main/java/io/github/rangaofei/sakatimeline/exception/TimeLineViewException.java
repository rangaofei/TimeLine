package io.github.rangaofei.sakatimeline.exception;

public enum TimeLineViewException implements TimeLineException {
    NULL_CONFIG(0x11, "TimeLineConfigure is null"),;

    TimeLineViewException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
