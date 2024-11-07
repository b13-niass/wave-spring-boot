package com.example.wavespringboot.validator;

import com.example.wavespringboot.web.dto.request.TransfertDTORequest;
import com.example.wavespringboot.validator.annotation.ValidMontantAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidMontantValidator implements ConstraintValidator<ValidMontantAnnotation, TransfertDTORequest> {

    @Override
    public void initialize(ValidMontantAnnotation constraintAnnotation) {
        // Initialization code, if necessary
    }

    @Override
    public boolean isValid(TransfertDTORequest dto, ConstraintValidatorContext context) {
        // Check if montantEnvoye is greater than or equal to montantRecus
        if (dto == null) return true; // or false based on your requirements
        return dto.montantEnvoye() >= dto.montantRecus();
    }
}
