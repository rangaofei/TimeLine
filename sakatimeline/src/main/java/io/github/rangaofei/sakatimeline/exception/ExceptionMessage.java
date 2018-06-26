package io.github.rangaofei.sakatimeline.exception;

public enum ExceptionMessage {
    LAYOUT_MANAGER_NULL(0x01, "the recyclerView's layoutManager is null"),
    UNKNOWN_ORIENTATION(0x02, "the recyclerView's layoutManager's orientation unknown"),
    NOT_LINEAR_LAYOUT_MANAGER(0x03, "the recyclerView's layoutManager is not LinearLayoutManager"),
    NOT_VERTICAL_ORIENTATION(0x04, "the recyclerView's layoutManager is not Vertical orientation"),
    NOT_HORIZONTAL_ORIENTATION(0x04, "the recyclerView's layoutManager is not Vertical orientation"),
    ADAPTER_NULL(0x05, "the adapter is null"),
    DRAWABLE_NULL(0x06, "the drawable is not correct!");

    ExceptionMessage(int code, String message) {
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
