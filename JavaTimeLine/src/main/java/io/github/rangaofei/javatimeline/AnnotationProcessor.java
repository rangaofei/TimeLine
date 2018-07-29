package io.github.rangaofei.javatimeline;

import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import io.github.rangaofei.javatimeline.TimeLineContext;
import io.github.rangaofei.javatimeline.processor.DividerProcessor;
import io.github.rangaofei.javatimeline.processor.TimeLineProcess;
import io.github.rangaofei.javatimeline.processor.TimeLineProcessor;
import io.github.rangaofei.libannotations.TimeLine;
import io.github.rangaofei.libannotations.TimeLineDividerAdapter;

/**
 * 注解处理入口类
 */
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {


    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> s = new HashSet<>();
        s.add(TimeLine.class.getCanonicalName());
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
        TimeLineContext.typeUtil = processingEnvironment.getTypeUtils();
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
            if (element.getKind() != ElementKind.CLASS) {
                TimeLineContext.error("%s can not annotated with other than class", TimeLine.class.getName());
                throw new RuntimeException("this element is not annotated with class");
            }

            createTimeLineAdapter(element);
        }

        Set<? extends Element> dividerElements = roundEnvironment.getElementsAnnotatedWith(TimeLineDividerAdapter.class);
        for (Element element : dividerElements) {
            TimeLineContext.note(element.getSimpleName().toString());
            if (element.getKind() != ElementKind.FIELD) {
                throw new RuntimeException("not field");
            }
            createDividerAdapter(element);
        }
        return true;
    }


    private void createTimeLineAdapter(Element element) {
        TimeLineProcess adapterProcessor = new TimeLineProcessor(element);
        adapterProcessor.processAnnotation();
    }

    private void createDividerAdapter(Element element) {
        TimeLineProcess dividerProcessor = new DividerProcessor(element);
        dividerProcessor.processAnnotation();
    }

}
