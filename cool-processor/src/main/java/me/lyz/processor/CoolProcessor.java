package me.lyz.processor;


import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import me.lyz.annotation.BindActivity;
import me.lyz.annotation.BindView;
import me.lyz.annotation.CoolAnnotation;

@AutoService(Processor.class)
public class CoolProcessor extends AbstractProcessor {


    private static final String ROUTING_CENTER_PATH = "/app/src/main/java";

    private static final String ROUTING_PACKAGE_NAME = "me.lyz.aptdemo";


    private Elements mElementUtil;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtil = processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> bindActivity = roundEnvironment.getElementsAnnotatedWith(BindActivity.class);
        if (bindActivity != null && bindActivity.size() != 0) {
            for (Element element : bindActivity) {
                TypeElement typeElement = (TypeElement) element;
                List<? extends Element> members = mElementUtil.getAllMembers(typeElement);
                MethodSpec.Builder bindViewMethodBuilder = MethodSpec.methodBuilder("bindView")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addParameter(ClassName.get(typeElement), "activity");

                for (Element member : members) {
                    BindView viewBinder = member.getAnnotation(BindView.class);
                    if (viewBinder != null) {
                        // TODO: 2018/8/15 0015 ID 
//                        bindViewMethodBuilder.addStatement("activity.$N = activity.findViewById($S)",
//                                member.getSimpleName(), viewBinder.value());
                    }
                }

                TypeSpec typeSpec = TypeSpec.classBuilder(typeElement.getSimpleName() + "_ViewBinding")
                        .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                        .addMethod(bindViewMethodBuilder.build())
                        .build();

                writeJavaFile(typeSpec);
            }
        }


        Set<? extends Element> coolAnnotation = roundEnvironment.getElementsAnnotatedWith(CoolAnnotation.class);
        if (coolAnnotation != null && coolAnnotation.size() != 0) {
            processCoolAnnotation();
        }
        return true;
    }

    private void processCoolAnnotation() {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello,cool-processor!")
                .build();

        TypeSpec coolClass = TypeSpec.classBuilder("CoolClass")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        writeJavaFile(coolClass, "me.lyz.aptdemo.apt");
    }

    private void writeJavaFile(TypeSpec typeSpec) {
        writeJavaFile(typeSpec, ROUTING_PACKAGE_NAME);
    }

    private void writeJavaFile(TypeSpec typeSpec, String packageName) {
        JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                .build();

        try {
            File currentDirectory = new File(".");
            String directoryPath = getTargetPath(currentDirectory.getCanonicalPath());
            File targetDirectory = new File(directoryPath);
            javaFile.writeTo(targetDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportAnnotationTypes = new LinkedHashSet<>();
        supportAnnotationTypes.add(CoolAnnotation.class.getCanonicalName());
        supportAnnotationTypes.add(BindActivity.class.getCanonicalName());
        return supportAnnotationTypes;
    }

    private String getTargetPath(String currentPath) {
        return currentPath + ROUTING_CENTER_PATH;
    }
}
