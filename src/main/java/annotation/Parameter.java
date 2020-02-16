package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description
 * @Author <a href="mailto:cenyingqiang@wxchina.com">yingqiang.Cen</a>
 * @Date 2020/2/14
 * @Version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Parameter {
    public String name();

    public String description() default "";

    public String defaultValue() default "";

    public Type type() default Type.STRING;

    public boolean required() default false;

    public String example() default "";

    public String validateRegex() default "";

    enum Type {
        STRING,
        LIST,
        INT,
        FILE
    }
}
