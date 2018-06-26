package io.github.rangaofei.javatimeline.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import io.github.rangaofei.javatimeline.AdapterUtil;
import io.github.rangaofei.javatimeline.annotations.TimeLine;
import io.github.rangaofei.javatimeline.annotations.TimeLineTextView;
import io.github.rangaofei.javatimeline.viewattr.ImageViewAttr;
import io.github.rangaofei.javatimeline.viewattr.TextViewAttr;


@AutoService(Processor.class)
public class TimeLineAnnotationProcessor extends AbstractProcessor {

    private Filer filter;

    private Messager messager;

    private Elements elementUtil;

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> s = new HashSet<>();
        s.add(TimeLine.class.getName());
        return s;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filter = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        elementUtil = processingEnvironment.getElementUtils();
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotationMirror, ExecutableElement executableElement, String s) {
        return super.getCompletions(element, annotationMirror, executableElement, s);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> timeLineElements = roundEnvironment.getElementsAnnotatedWith(TimeLine.class);
        for (Element element : timeLineElements) {
            note(element.getSimpleName().toString());
            if (element.getKind() == ElementKind.CLASS) {
                note("correct element");
            } else {
                throw new RuntimeException("this element is not annotated with class");
            }

            String packageName = elementUtil.getPackageOf(element).getQualifiedName().toString();
            note(packageName);
            try {
                createAdapter(element);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void note(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    private void note(String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }

    private void createAdapter(Element element) throws IOException {
        String packageName = elementUtil.getPackageOf(element).getQualifiedName().toString();
        String className = element.getSimpleName().toString();
        String fullClassName = packageName + "." + className;
        String adapterName = AdapterUtil.generateAdapterName(element, className);
        note("----getName");

        String keyLayoutId = element.getAnnotation(TimeLine.class).keyLayoutId();
        String valueLayoutId = element.getAnnotation(TimeLine.class).valueLayoutId();
        List<TextViewAttr> keyTextView = new ArrayList<>();
        List<ImageViewAttr> keyImageView = new ArrayList<>();
        List<TextViewAttr> valueTextView = new ArrayList<>();
        List<ImageViewAttr> valueImageView = new ArrayList<>();
        AdapterUtil.getTextViewAttr(keyTextView, valueTextView, element);
        note("----getTextViewAttr");

        AdapterUtil.getImageViewAttr(keyImageView, valueImageView, element);

        MethodSpec constructor = AdapterUtil.constructorMethod(fullClassName);
        note("----constructor");


        MethodSpec getKeyLayoutIdMethod = AdapterUtil.generateOverRideIdMethod(keyLayoutId,
                "getKeyLayoutId");
        note("----getKeyLayoutIdMethod");
        MethodSpec getValueLayoutIdMethod = AdapterUtil.generateOverRideIdMethod(valueLayoutId,
                "getValueLayoutId");

        note("----getValueLayoutIdMethod");
        MethodSpec bindKeyItemMethod = AdapterUtil.generateBindMethod("bindKeyItem",
                fullClassName, keyTextView, keyImageView, "KeyViewHolder");

        note("----bindKeyItemMethod");
        MethodSpec bindValueItemMethod = AdapterUtil.generateBindMethod("bindValueItem",
                fullClassName, valueTextView, valueImageView, "ValueViewHolder");

        note("----bindValueItemMethod");
        TypeName superClass = ParameterizedTypeName
                .get(ClassName.bestGuess("io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter"), ClassName.bestGuess(element.getSimpleName().toString()));
        TypeSpec adapter = TypeSpec.classBuilder(adapterName)
                .superclass(superClass)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethods(Arrays.asList(constructor, getKeyLayoutIdMethod, getValueLayoutIdMethod,
                        bindKeyItemMethod, bindValueItemMethod))
                .build();
        JavaFile javaFile = JavaFile.builder(packageName, adapter)
                .build();
        javaFile.writeTo(filter);
    }

}
