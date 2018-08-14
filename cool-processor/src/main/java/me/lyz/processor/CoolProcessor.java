package me.lyz.processor;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import me.lyz.annotation.CoolAnnotation;

@AutoService(Processor.class)
public class CoolProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello , cool-processor!")
                .build();

        TypeSpec coolClass = TypeSpec.classBuilder("CoolClass")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("me.lyz.aptdemo", coolClass)
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(CoolAnnotation.class.getCanonicalName());
    }
}
