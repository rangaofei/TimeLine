package io.github.rangaofei.javatimeline.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.util.ElementFilter;

import io.github.rangaofei.javatimeline.TimeLineContext;
import io.github.rangaofei.javatimeline.utils.StringUtil;
import io.github.rangaofei.javatimeline.viewattr.AnchorInfo;
import io.github.rangaofei.javatimeline.viewattr.ImageViewAttr;
import io.github.rangaofei.libannotations.TimeLineImageView;

public class ImageViewProcessor implements TimeLineProcess {
    private Element element;
    private List<AnchorInfo> anchorInfoList;
    private CodeBlock keyCodeBlock;
    private CodeBlock valueCodeBlock;
    private List<ImageViewAttr> imageViewAttrList;
    private ClassName imageView;

    public ImageViewProcessor(Element element, List<AnchorInfo> anchorInfoList) {
        this.element = element;
        this.anchorInfoList = anchorInfoList;
        imageViewAttrList = new ArrayList<>();
        imageView = ClassName.bestGuess("android.widget.ImageView");
    }


    public CodeBlock getKeyCodeBlock() {
        return keyCodeBlock;
    }

    public CodeBlock getValueCodeBlock() {
        return valueCodeBlock;
    }

    @Override
    public void processAnnotation() {
        getImageViewAttr();
        keyCodeBlock = generateImageViewCode(true);
        valueCodeBlock = generateImageViewCode(false);
    }

    private void getImageViewAttr() {
        if (this.element == null) {
            return;
        }
        for (Element e : ElementFilter.fieldsIn(element.getEnclosedElements())) {
            if (e.getAnnotation(TimeLineImageView.class) != null) {
                TimeLineImageView imageView = e.getAnnotation(TimeLineImageView.class);
                String filedName = e.getSimpleName().toString();
                ImageViewAttr imageViewAttr = new ImageViewAttr(imageView.id(), filedName, imageView.key());
                imageViewAttrList.add(imageViewAttr);

            }
        }
    }

    private CodeBlock generateImageViewCode(boolean isKey) {
        String holderName = null;
        if (isKey) {
            holderName = "KeyViewHolder";
        } else {
            holderName = "ValueViewHolder";
        }
        CodeBlock.Builder builder = CodeBlock.builder();
        for (int i = 0; i < imageViewAttrList.size(); i++) {
            ImageViewAttr imageViewAttr = imageViewAttrList.get(i);
            String filedName = "imageView_" + i;
            if (isKey && !imageViewAttr.isKey()) {
                continue;
            }
            if (!isKey && imageViewAttr.isKey()) {
                continue;
            }
            if (!StringUtil.isResId(imageViewAttr.getImageViewId())) {
                logError("%s is not a standard resId format", imageViewAttr.getImageViewId());
            }
            builder.addStatement("$T $L = (($L)holder).itemView.findViewById($L)",
                    imageView, filedName, holderName, imageViewAttr.getImageViewId());
            builder.addStatement("$L.setImageResource(data.$L)", filedName, imageViewAttr.getImageSrc());
//            if (anchorInfoList.size() < 1 ||
//                    !anchorInfoList.get(0).getAnchorIds().contains(imageViewAttr.getImageViewId())) {
//                if (!imageViewAttr.getImageSrc().equals(TimeConfig.ID_NULL)) {
//
//                }
//
//            } else {
//                builder.beginControlFlow("if (data.$L)", anchorInfoList.get(0).getFieldName());
//                if (!imageViewAttr.getStyleAnchorId().equals(TimeConfig.ID_NULL)) {
//                    generateTextViewProxyCode(builder, filedName, imageViewAttr.getStyleAnchorId());
//                }
//                builder.nextControlFlow("else ");
//                if (!imageViewAttr.getStyleId().equals(TimeConfig.ID_NULL)) {
//                    generateTextViewProxyCode(builder, filedName, imageViewAttr.getStyleId());
//                }
//                builder.endControlFlow();
//                if (imageViewAttr.getTextString() != null) {
//                    builder.addStatement("$L.setText(data.$L)", filedName, imageViewAttr.getTextString());
//                }
//            }
        }
        return builder.build();
    }

    private void logError(String msg, Object... args) {
        TimeLineContext.error(msg, args);
    }

}
