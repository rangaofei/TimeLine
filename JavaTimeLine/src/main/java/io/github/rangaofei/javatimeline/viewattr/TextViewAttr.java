package io.github.rangaofei.javatimeline.viewattr;

import com.squareup.javapoet.CodeBlock;

import io.github.rangaofei.javatimeline.annotations.TimeLine;
import io.github.rangaofei.javatimeline.annotations.TimeLineTextView;

public class TextViewAttr {
    private String textViewId;
    private String textString;
    private boolean isKey;
    private String styleId;
    private String styleAnchorId;

    public TextViewAttr(String textString, TimeLineTextView textView) {
        this.textViewId = textView.id();
        this.textString = textString;
        this.isKey = textView.key();
        this.styleId = textView.style();
        this.styleAnchorId = textView.styleAnchor();
    }

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


    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getStyleAnchorId() {
        return styleAnchorId;
    }

    public void setStyleAnchorId(String styleAnchorId) {
        this.styleAnchorId = styleAnchorId;
    }

}
