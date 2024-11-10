package com.example.wavespringboot.validator;

import com.example.wavespringboot.data.repository.UserRepository;
import com.example.wavespringboot.validator.annotation.IfExistUserAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class IfExistUser implements ConstraintValidator<IfExistUserAnnotation, String> {

    private final UserRepository userRepository;

    // Constructor injection for UserRepository
    public IfExistUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(IfExistUserAnnotation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String telephone, ConstraintValidatorContext context) {
        if (telephone == null) {
            return false;
        }
        return userRepository.existsByTelephone(telephone);
    }
}
