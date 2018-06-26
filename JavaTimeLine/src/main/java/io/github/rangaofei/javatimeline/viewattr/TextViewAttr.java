package io.github.rangaofei.javatimeline.viewattr;

import com.squareup.javapoet.CodeBlock;

public class TextViewAttr {
    private String textViewId;
    private String textString;
    private String textColor;
    private String textSize;
    private String backColor;
    private boolean isKey;

    public String getTextViewId() {
        return textViewId;
    }

    public void setTextViewId(String textViewId) {
        this.textViewId = textViewId;
    }

    public String getTextString() {
        return textString;
    }

    public void setTextString(String textString) {
        this.textString = textString;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }


    public CodeBlock addTextCode(String holderName) {
        return CodeBlock.builder()
                .addStatement("((android.widget.TextView) (((" + holderName +
                                ")holder).itemView.findViewById($L))).setText(data.$L)",
                        textViewId, textString).build();
    }
}
