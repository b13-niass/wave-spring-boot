package com.example.wavespringboot.validator;

import com.example.wavespringboot.data.repository.UserRepository;
import com.example.wavespringboot.validator.annotation.TelValidUniqueAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

public class TelValidUnique implements ConstraintValidator<TelValidUniqueAnnotation, String> {
    private static final String TELEPHONE_PATTERN = "^\\+221(77|76|78)\\d{7}$";

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(TelValidUniqueAnnotation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String telephone, ConstraintValidatorContext context) {
        if (telephone == null || !Pattern.compile(TELEPHONE_PATTERN).matcher(telephone).matches()) {
            return false;
        }

        return !userRepository.existsByTelephone(telephone);
    }
}
