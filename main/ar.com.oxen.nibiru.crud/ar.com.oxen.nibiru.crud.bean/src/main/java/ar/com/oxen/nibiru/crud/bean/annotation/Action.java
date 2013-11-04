package ar.com.oxen.nibiru.crud.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
	String name();

	boolean requiresEntity();

	boolean requiresConfirmation() default false;

	boolean showInList() default true;

	boolean showInForm() default true;
	
	String[] allowedRoles() default {};
	
}
