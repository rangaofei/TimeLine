package io.github.rangaofei.timeline;

import io.github.rangaofei.javatimeline.annotations.TimeLine;
import io.github.rangaofei.javatimeline.annotations.TimeLineAnchor;
import io.github.rangaofei.javatimeline.annotations.TimeLineTextView;

@TimeLine(valueLayoutId = "R.layout.item_value")
public class StepViewModel {

    @TimeLineTextView(key = false, id = "R.id.value", styleAnchor = "R.style.StepView2")
    public String text;
    @TimeLineAnchor({"R.id.value","R.id.time"})
    public boolean right;

    @TimeLineTextView(key = false, id = "R.id.time", styleAnchor = "R.style.StepView3")
    public String time;

    public StepViewModel(String text, boolean right, String time) {
        this.text = text;
        this.right = right;
        this.time = time;
    }

    public StepViewModel(String text, boolean right) {
        this.text = text;
        this.right = right;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
