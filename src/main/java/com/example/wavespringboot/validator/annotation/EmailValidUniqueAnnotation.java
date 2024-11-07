package com.example.wavespringboot.validator.annotation;

import com.example.wavespringboot.validator.EmailValidUnique;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidUnique.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidUniqueAnnotation {
    String message() default "Email déjà existant";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
