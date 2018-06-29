package io.github.rangaofei.javatimeline.processor;

import java.util.List;

import javax.lang.model.element.Element;

import io.github.rangaofei.javatimeline.viewattr.AnchorInfo;

public class ViewProcessor implements TimeLineProcess {
    private Element element;
    private List<AnchorInfo> anchorInfoList;

    public ViewProcessor(Element element, List<AnchorInfo> anchorInfoList) {
        this.element = element;
        this.anchorInfoList = anchorInfoList;
    }

    @Override
    public void processAnnotation() {

    }
}
