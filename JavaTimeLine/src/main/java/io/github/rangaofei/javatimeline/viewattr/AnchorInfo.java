package io.github.rangaofei.javatimeline.viewattr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnchorInfo {
    private String fieldName;
    private List<String> anchorIds;

    public AnchorInfo() {
        anchorIds = new ArrayList<>();
    }

    public AnchorInfo(String fieldName, List<String> anchorIds) {
        this.fieldName = fieldName;
        this.anchorIds = anchorIds;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getAnchorIds() {
        return anchorIds;
    }

    public void setAnchorIds(List<String> anchorIds) {
        this.anchorIds = anchorIds;
    }

    @Override
    public String toString() {
        return "AnchorInfo{" +
                "fieldName='" + fieldName + '\'' +
                ", anchorIds=" + Arrays.toString(anchorIds.toArray()) +
                '}';
    }
}
