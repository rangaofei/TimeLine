package io.github.rangaofei.timeline;

import io.github.rangaofei.javatimeline.annotations.TimeLine;
import io.github.rangaofei.javatimeline.annotations.TimeLineTextView;

@TimeLine(valueLayoutId = "R.layout.item_value")
public class StepViewModel {

    @TimeLineTextView(key = false, value = "R.id.value")
    public String text;


    public StepViewModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
