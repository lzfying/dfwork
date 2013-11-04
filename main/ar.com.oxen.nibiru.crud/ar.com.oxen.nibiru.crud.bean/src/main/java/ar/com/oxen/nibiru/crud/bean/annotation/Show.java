package ar.com.oxen.nibiru.crud.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Show {
	boolean inList() default true;

	boolean inForm() default true;

	int order() default 0;

	int columWidth() default -1;
}
