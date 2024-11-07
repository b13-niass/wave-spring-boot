package com.example.wavespringboot.validator.annotation;

import com.example.wavespringboot.validator.TelValidUnique;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TelValidUnique.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TelValidUniqueAnnotation {
    String message() default "Telephone déjà existant";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
