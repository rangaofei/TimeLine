package io.github.rangaofei.javatimeline;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.Arrays;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.ElementFilter;

import io.github.rangaofei.javatimeline.annotations.TimeLine;
import io.github.rangaofei.javatimeline.annotations.TimeLineImageView;
import io.github.rangaofei.javatimeline.annotations.TimeLineTextView;
import io.github.rangaofei.javatimeline.viewattr.ImageViewAttr;
import io.github.rangaofei.javatimeline.viewattr.TextViewAttr;

import static io.github.rangaofei.javatimeline.TimeConfig.NULL;

public class AdapterUtil {

    public static String generateAdapterName(Element element, String className) {
        String generatedName = element.getAnnotation(TimeLine.class).name().trim();
        if (generatedName.equals("")) {
            generatedName = className.trim() + "Adapter";
        }

        return generatedName;
    }

    public static MethodSpec constructorMethod(String fullClassName) {
        ClassName list = ClassName.get(java.util.List.class);
        TypeName listT = ParameterizedTypeName.get(list, ClassName.bestGuess(fullClassName));
        ParameterSpec constructorParameter = ParameterSpec.builder(listT, "list")
                .build();
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(constructorParameter)
                .addStatement("super(list)")
//                .addStatement("model = new $T()", ClassName.bestGuess(fullClassName))
                .build();
    }

    public static MethodSpec generateOverRideIdMethod(String layoutId, String methodName) {
        return MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(int.class)
                .addStatement("return $L", layoutId)
                .build();

    }

    public static MethodSpec generateBindMethod(String methodName,
                                                String fullClassName,
                                                List<TextViewAttr> textView,
                                                List<ImageViewAttr> imageView,
                                                String holderName) {
        ParameterSpec one = ParameterSpec
                .builder(ClassName.bestGuess("io.github.rangaofei.sakatimeline.adapter.BaseViewHolder"), "holder")
                .build();
        ParameterSpec two = ParameterSpec
                .builder(ClassName.bestGuess(fullClassName), "data")
                .build();

        MethodSpec.Builder bindItemMethod = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameters(Arrays.asList(one, two));
        for (TextViewAttr attr : textView) {
            bindItemMethod.addStatement("((android.widget.TextView) (((" + holderName +
                            ")holder).itemView.findViewById($L))).setText(data.$L)",
                    attr.getTextViewId(), attr.getTextString());
            if (attr.getTextColor() != null) {
                bindItemMethod.addStatement("((android.widget.TextView) (((" + holderName +
                                ")holder).itemView.findViewById($L))).setTextColor($L)",
                        attr.getTextViewId(), attr.getTextColor());
            }
            if (attr.getTextSize() != null) {
                bindItemMethod.addStatement("((android.widget.TextView) (((" + holderName +
                                ")holder).itemView.findViewById($L))).setTextSize($L)",
                        attr.getTextViewId(), attr.getTextSize());
            }
            if (attr.getTextSize() != null) {
                bindItemMethod.addStatement("((android.widget.TextView) (((" + holderName +
                                ")holder).itemView.findViewById($L))).setBackgroundColor($L)",
                        attr.getTextViewId(), attr.getBackColor());
            }
        }

        for (ImageViewAttr attr : imageView) {
            bindItemMethod.addStatement("((android.widget.ImageView) (((" + holderName +
                            ")holder).itemView.findViewById($L))).setImageResource(data.$L)",
                    attr.getImageViewId(), attr.getImageSrc());
        }
        return bindItemMethod.build();

    }

    public static void getTextViewAttr(List<TextViewAttr> keyText,
                                       List<TextViewAttr> valueText,
                                       Element element) {
        for (Element e : ElementFilter.fieldsIn(element.getEnclosedElements())) {

            if (e.getAnnotation(TimeLineTextView.class) != null) {
                TimeLineTextView timeLineTextView = e.getAnnotation(TimeLineTextView.class);
                TextViewAttr textViewAttr = new TextViewAttr();
                textViewAttr.setTextViewId(timeLineTextView.value());
                textViewAttr.setTextString(e.getSimpleName().toString());
                if (!timeLineTextView.textColor().equals(NULL)) {
                    textViewAttr.setTextColor(timeLineTextView.textColor());
                }
                if (!timeLineTextView.textSize().equals(NULL)) {
                    textViewAttr.setTextSize(timeLineTextView.textSize());
                }
                if (!timeLineTextView.backGroundColor().equals(NULL)) {
                    textViewAttr.setBackColor(timeLineTextView.backGroundColor());
                }
                boolean isKey = timeLineTextView.key();
                if (isKey) {
                    keyText.add(textViewAttr);
                } else {
                    valueText.add(textViewAttr);
                }
            }
        }

    }

    public static void getImageViewAttr(List<ImageViewAttr> keyImageView,
                                        List<ImageViewAttr> valueImageView,
                                        Element element) {
        for (Element e : ElementFilter.fieldsIn(element.getEnclosedElements())) {

            if (e.getAnnotation(TimeLineImageView.class) != null) {
                TimeLineImageView timeLineImageView = e.getAnnotation(TimeLineImageView.class);
                ImageViewAttr imageViewAttr = new ImageViewAttr();
                imageViewAttr.setImageViewId(timeLineImageView.value());
                imageViewAttr.setImageSrc(e.getSimpleName().toString());

                boolean isKey = timeLineImageView.key();
                if (isKey) {
                    keyImageView.add(imageViewAttr);
                } else {
                    valueImageView.add(imageViewAttr);
                }
            }
        }

    }
}
