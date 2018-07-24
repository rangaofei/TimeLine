package io.github.rangaofei.timeline;


import io.github.rangaofei.libannotations.TimeLine;
import io.github.rangaofei.libannotations.TimeLineAnchor;
import io.github.rangaofei.libannotations.TimeLineImageView;
import io.github.rangaofei.libannotations.TimeLineTextView;

@TimeLine(valueLayoutId = "R.layout.item_value")
public class StepViewModel {

    @TimeLineTextView(key = false, id = "R.id.value", style = "R.style.StepView1", styleAnchor = "R.style.StepView2")
    public String text;
    @TimeLineAnchor({"R.id.value", "R.id.time"})
    public boolean right;

    @TimeLineTextView(key = false, id = "R.id.time", style = "R.style.StepView1",styleAnchor= "R.style.StepView3")
    public String time;

    @TimeLineImageView(key = false, id = "R.id.iv")
    public int imgSrc;

    public StepViewModel(String text, boolean right, String time, int imgSrc) {
        this.text = text;
        this.right = right;
        this.time = time;
        this.imgSrc = imgSrc;
    }

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
