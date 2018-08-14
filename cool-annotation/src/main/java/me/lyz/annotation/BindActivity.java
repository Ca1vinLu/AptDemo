package me.lyz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LYZ 2018.08.14
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface BindActivity {
}
