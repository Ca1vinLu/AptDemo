package me.lyz.processor;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
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


    private static final String ROUTING_CENTER_PATH = "/app/src/main/java";

    private static final String ROUTING_PACKAGE_NAME = "me.lyz.aptdemo.apt";

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

        JavaFile javaFile = JavaFile.builder(ROUTING_PACKAGE_NAME, coolClass)
                .build();

        try {
            File currentDirectory = new File(".");
            String directoryPath = getTargetPath(currentDirectory.getCanonicalPath());
            File targetDirectory = new File(directoryPath);
            javaFile.writeTo(targetDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(CoolAnnotation.class.getCanonicalName());
    }

    private String getTargetPath(String currentPath) {
        return currentPath + ROUTING_CENTER_PATH;
    }
}
