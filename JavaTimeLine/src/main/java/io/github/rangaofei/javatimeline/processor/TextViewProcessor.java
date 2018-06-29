package io.github.rangaofei.javatimeline.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.util.ElementFilter;

import io.github.rangaofei.javatimeline.TimeConfig;
import io.github.rangaofei.javatimeline.annotations.TimeLineTextView;
import io.github.rangaofei.javatimeline.viewattr.AnchorInfo;
import io.github.rangaofei.javatimeline.viewattr.TextViewAttr;

public class TextViewProcessor implements TimeLineProcess {

    private Element element;
    private List<TextViewAttr> textViewAttrList;
    private CodeBlock keyCodeBlock;
    private CodeBlock valueCodeBlock;
    private ClassName textView;
    private List<AnchorInfo> anchorInfoList;

    public TextViewProcessor(Element element, List<AnchorInfo> anchorInfoList) {
        this.element = element;
        this.anchorInfoList = anchorInfoList;
        textViewAttrList = new ArrayList<>();
        textView = ClassName.bestGuess("android.widget.TextView");
    }

    public CodeBlock getKeyCodeBlock() {
        return keyCodeBlock;
    }

    public CodeBlock getValueCodeBlock() {
        return valueCodeBlock;
    }

    @Override
    public void processAnnotation() {
        getStyleId();
        keyCodeBlock = generateTextViewCodeBlock(true);
        valueCodeBlock = generateTextViewCodeBlock(false);
    }

    private void getStyleId() {
        if (this.element == null) {
            return;
        }
        for (Element e : ElementFilter.fieldsIn(element.getEnclosedElements())) {
            if (e.getAnnotation(TimeLineTextView.class) != null) {
                TimeLineTextView textView = e.getAnnotation(TimeLineTextView.class);
                String filedName = e.getSimpleName().toString();
                TextViewAttr textViewAttr = new TextViewAttr(filedName, textView);
                textViewAttrList.add(textViewAttr);

            }
        }

    }

    private CodeBlock generateTextViewCodeBlock(boolean isKey) {
        String holderName = null;
        if (isKey) {
            holderName = "KeyViewHolder";
        } else {
            holderName = "ValueViewHolder";
        }
        CodeBlock.Builder builder = CodeBlock.builder();
        for (int i = 0; i < textViewAttrList.size(); i++) {
            TextViewAttr textViewAttr = textViewAttrList.get(i);
            String filedName = "textView_" + i;
            if (isKey && !textViewAttr.isKey()) {
                continue;
            }
            if (!isKey && textViewAttr.isKey()) {
                continue;
            }
            builder.addStatement("$T $L = (($L)holder).itemView.findViewById($L)",
                    textView, filedName, holderName, textViewAttr.getTextViewId());
            if (anchorInfoList.size() < 1 ||
                    !anchorInfoList.get(0).getAnchorIds().contains(textViewAttr.getTextViewId())) {
                if (!textViewAttr.getStyleId().equals(TimeConfig.ID_NULL)) {
                    generateTextViewProxyCode(builder, filedName, textViewAttr.getStyleId());
//                    builder.addStatement("$L.setTextAppearance($L)",
//                            filedName, textViewAttr.getStyleId());
                }
                if (textViewAttr.getTextString() != null) {
                    builder.addStatement("$L.setText(data.$L)", filedName, textViewAttr.getTextString());
                }
            } else {
                builder.beginControlFlow("if (data.$L)", anchorInfoList.get(0).getFieldName());
                if (!textViewAttr.getStyleAnchorId().equals(TimeConfig.ID_NULL)) {
                    generateTextViewProxyCode(builder, filedName, textViewAttr.getStyleAnchorId());
                }
                builder.nextControlFlow("else ");
                if (!textViewAttr.getStyleId().equals(TimeConfig.ID_NULL)) {
                    generateTextViewProxyCode(builder, filedName, textViewAttr.getStyleId());
                }
                builder.endControlFlow();
                if (textViewAttr.getTextString() != null) {
                    builder.addStatement("$L.setText(data.$L)", filedName, textViewAttr.getTextString());
                }
            }
        }
        return builder.build();
    }

    private void generateTextViewProxyCode(CodeBlock.Builder builder, String fieldName, String styleId) {
        ClassName textViewInterface =
                ClassName.bestGuess("io.github.rangaofei.sakatimeline.proxy.TextViewInterface");
        ClassName textViewProxy =
                ClassName.bestGuess("io.github.rangaofei.sakatimeline.proxy.TextViewProxy");
        ClassName textViewProxyHandler =
                ClassName.bestGuess("io.github.rangaofei.sakatimeline.proxy.TextViewProxyHandler");
        ClassName proxy =
                ClassName.bestGuess("java.lang.reflect.Proxy");
        builder.addStatement("$T textViewInterface = new $T($L)",
                textViewInterface, textViewProxy, fieldName);
        builder.addStatement("$T textProxy=($T)$T.newProxyInstance(\n" +
                        "                TextViewInterface.class.getClassLoader(),\n" +
                        "                new Class[]{TextViewInterface.class},\n" +
                        "                new $T(textViewInterface))",
                textViewInterface, textViewInterface, proxy, textViewProxyHandler);
        builder.addStatement("textProxy.setTextAppearance($L.getContext(), $L)",
                fieldName, styleId);
    }
}
