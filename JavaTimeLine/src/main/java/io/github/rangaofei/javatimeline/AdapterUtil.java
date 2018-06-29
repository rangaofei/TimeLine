package io.github.rangaofei.javatimeline;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
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
                                                CodeBlock... codeBlocks) {
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
        for (CodeBlock codeBlock : codeBlocks) {
            bindItemMethod.addCode(codeBlock);
        }
        return bindItemMethod.build();

    }


    public static List<CodeBlock> getImageViewAttr(List<ImageViewAttr> imageViewAttrList, String holderName) {
        List<CodeBlock> codeBlockList = new ArrayList<>();
        ClassName imageView = ClassName.bestGuess("android.widget.ImageView");
        for (int i = 0; i < imageViewAttrList.size(); i++) {
            ImageViewAttr imageViewAttr = imageViewAttrList.get(i);
            String filedName = "imageView_" + i;
            CodeBlock codeBlock = CodeBlock.builder()
                    .addStatement("$T $L = (($L)holder).itemView.findViewById($L)",
                            imageView, filedName, holderName, imageViewAttr.getImageViewId())
                    .addStatement("$L.setImageResource(data.$L)",
                            filedName, imageViewAttr.getImageSrc())
                    .build();
            codeBlockList.add(codeBlock);
        }

        return codeBlockList;

    }
}
