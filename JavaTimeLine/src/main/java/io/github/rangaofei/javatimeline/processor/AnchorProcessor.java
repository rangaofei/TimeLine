package io.github.rangaofei.javatimeline.processor;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.util.ElementFilter;

import io.github.rangaofei.javatimeline.TimeLineContext;
import io.github.rangaofei.javatimeline.viewattr.AnchorInfo;
import io.github.rangaofei.libannotations.TimeLineAnchor;

public class AnchorProcessor implements TimeLineProcess {
    private Element element;
    private List<AnchorInfo> anchorInfos;


    public AnchorProcessor(Element element) {
        this.element = element;
        anchorInfos = new ArrayList<>();
    }

    public List<AnchorInfo> getAnchorInfos() {
        return anchorInfos;
    }

    @Override
    public void processAnnotation() {
        fillAnchorList();
    }

    private void fillAnchorList() {
        if (this.element == null) {
            return;
        }
        for (Element e : ElementFilter.fieldsIn(element.getEnclosedElements())) {
            if (e.getAnnotation(TimeLineAnchor.class) != null) {
                AnchorInfo anchorInfo = new AnchorInfo();
                TimeLineAnchor timeLineAnchor = e.getAnnotation(TimeLineAnchor.class);
                String filedName = e.getSimpleName().toString();
                anchorInfo.setFieldName(filedName);
                for (String s : timeLineAnchor.value()) {
                    anchorInfo.getAnchorIds().add(s);
                }
                anchorInfos.add(anchorInfo);
            }
        }

        if (anchorInfos.size() > 1) {
            throw new RuntimeException("one TimeLine Annotation class must not have more than one anchor");
        }
        noteAnchorInfo();
    }

    private void noteAnchorInfo() {
        if (this.anchorInfos.size() < 1) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (AnchorInfo anchorInfo : anchorInfos) {
            sb.append(anchorInfo.toString());
        }
        TimeLineContext.note(sb.toString());
    }
}
