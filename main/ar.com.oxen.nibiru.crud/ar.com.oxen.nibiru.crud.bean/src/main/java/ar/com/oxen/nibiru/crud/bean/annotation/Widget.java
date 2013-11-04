package ar.com.oxen.nibiru.crud.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ar.com.oxen.nibiru.crud.manager.api.WidgetType;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Widget {
	WidgetType type();

	boolean readonly() default false;

	String[] values() default {};

	String valuesFilterExpression() default "";

	int maxLength() default -1;

	String tab() default "general";
}
