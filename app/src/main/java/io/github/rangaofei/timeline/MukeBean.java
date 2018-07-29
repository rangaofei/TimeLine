package io.github.rangaofei.timeline;


import io.github.rangaofei.libannotations.TimeLine;
import io.github.rangaofei.libannotations.TimeLineTextView;

@TimeLine(valueLayoutId = "R.layout.item_muke")
public class MukeBean {
    @TimeLineTextView(key = false, id = "R.id.time")
    public String time;
    @TimeLineTextView(key = false, id = "R.id.content")
    public String content;

    public MukeBean(String time, String content) {
        this.time = time;
        this.content = content;
    }
}
