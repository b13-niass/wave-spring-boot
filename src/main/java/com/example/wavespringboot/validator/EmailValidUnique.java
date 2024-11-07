package com.example.wavespringboot.validator;

import com.example.wavespringboot.data.repository.UserRepository;
import com.example.wavespringboot.validator.annotation.EmailValidUniqueAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

public class EmailValidUnique implements ConstraintValidator<EmailValidUniqueAnnotation, String> {

    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(EmailValidUniqueAnnotation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || !Pattern.compile(EMAIL_PATTERN).matcher(email).matches()) {
            return false;
        }

        return !userRepository.existsByEmail(email);
    }
}
