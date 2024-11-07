package com.example.wavespringboot.validator.annotation;

import com.example.wavespringboot.validator.ValidMontantValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidMontantValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMontantAnnotation {
    String message() default "Le montant envoyé doit être supérieur ou égal au montant reçu";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
