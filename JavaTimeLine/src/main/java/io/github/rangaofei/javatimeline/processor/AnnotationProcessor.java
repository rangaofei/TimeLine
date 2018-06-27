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
import io.github.rangaofei.javatimeline.TimeLineContext;
import io.github.rangaofei.javatimeline.annotations.TimeLine;
import io.github.rangaofei.javatimeline.annotations.TimeLineTextView;
import io.github.rangaofei.javatimeline.viewattr.ImageViewAttr;
import io.github.rangaofei.javatimeline.viewattr.TextViewAttr;


@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {


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
        TimeLineContext.filter = processingEnvironment.getFiler();
        TimeLineContext.messager = processingEnvironment.getMessager();
        TimeLineContext.elementUtil = processingEnvironment.getElementUtils();
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotationMirror, ExecutableElement executableElement, String s) {
        return super.getCompletions(element, annotationMirror, executableElement, s);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> timeLineElements = roundEnvironment.getElementsAnnotatedWith(TimeLine.class);
        for (Element element : timeLineElements) {
            TimeLineContext.note(element.getSimpleName().toString());
            if (element.getKind() == ElementKind.CLASS) {
                TimeLineContext.note("correct element");
            } else {
                throw new RuntimeException("this element is not annotated with class");
            }

            createAdapter(element);
        }
        return true;
    }


    private void createAdapter(Element element) {
        TimeLineProcess adapterProcessor = new TimeLineProcessor(element);
        adapterProcessor.processAnnotation();
    }

}
