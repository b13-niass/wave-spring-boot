package com.example.wavespringboot.validator.annotation;

import com.example.wavespringboot.validator.IfExistUser;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IfExistUser.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IfExistUserAnnotation {
    String message() default "Le destinataire n'existe pas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

