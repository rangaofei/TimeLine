package io.github.rangaofei.javatimeline.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Element;

import io.github.rangaofei.javatimeline.annotations.TimeLineAnchor;

public class TimeLineAnchorProcessor implements TimeLineProcess {
    private List<Integer> anchorIds;
    private Element element;
    private String filedName;


    public TimeLineAnchorProcessor(Element element) {
        this.element = element;
        anchorIds = new ArrayList<>();
    }

    public List<Integer> getAnchorIds() {
        return anchorIds;
    }


    @Override
    public void processAnnotation() {
        if (this.element == null) {
            return;
        }
        filedName = element.getSimpleName().toString();
        for (int i : element.getAnnotation(TimeLineAnchor.class).Ids()) {
            anchorIds.add(i);
        }
    }
}
