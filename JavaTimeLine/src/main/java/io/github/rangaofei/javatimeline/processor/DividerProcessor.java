package io.github.rangaofei.javatimeline.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Arrays;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import io.github.rangaofei.javatimeline.AdapterUtil;
import io.github.rangaofei.javatimeline.TimeLineContext;

public class DividerProcessor implements TimeLineProcess {
    private Element element;

    private String packageName;
    private String className;
    private String fullClassName;
    private String adapterName;

    private MethodSpec constructorMethod;

    public DividerProcessor(Element element) {
        this.element = element;
    }

    @Override
    public void processAnnotation() {
        getBasicField();
        generateConstructor();
        try {
            generateAdapter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getBasicField() {
        if (this.element == null) {
            throw new RuntimeException("element is null");
        }
        TimeLineContext.note("---1");
        packageName = TimeLineContext.elementUtil.getPackageOf(element).getQualifiedName().toString();
        TimeLineContext.note("---2");
        className = element.getSimpleName().toString();
        TimeLineContext.note("---3");
        fullClassName = packageName + "." + className;
        adapterName = AdapterUtil.generateDividerAdapterName(element, className);
        TimeLineContext.note("---4");
        noteBasicInfo();
    }

    private void generateConstructor() {
        ClassName list = ClassName.get(java.util.List.class);
        TypeName listT = ParameterizedTypeName.get(list, ClassName.bestGuess("android.graphics.drawable.Drawable"));
        ParameterSpec constructorParameter = ParameterSpec.builder(listT, "list")
                .build();
        constructorMethod = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(constructorParameter)
                .addStatement("super(list)").build();
    }

    private void generateAdapter() throws IOException {
        if (this.element == null) {
            return;
        }
        TypeSpec adapter = TypeSpec.classBuilder(adapterName)
                .superclass(ClassName.bestGuess("io.github.rangaofei.sakatimeline.divider.DividerLayoutAdapter"))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethods(Arrays.asList(constructorMethod))
                .build();
        JavaFile javaFile = JavaFile.builder(packageName, adapter)
                .build();
        javaFile.writeTo(TimeLineContext.filter);
    }

    /**
     * 日志输出标注的类的基本信息
     */
    private void noteBasicInfo() {
        TimeLineContext.note(">>>>packageName=%s", packageName);
        TimeLineContext.note(">>>>className=%s", className);
        TimeLineContext.note(">>>>fullClassName=%s", fullClassName);
        TimeLineContext.note(">>>>adapterName=%s", adapterName);
    }

}
