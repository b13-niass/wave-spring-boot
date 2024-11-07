package com.example.wavespringboot.validator;

import com.example.wavespringboot.data.repository.UserRepository;
import com.example.wavespringboot.validator.annotation.IfExistUserAnnotation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class IfExistUser implements ConstraintValidator<IfExistUserAnnotation, Long> {

    private final UserRepository userRepository;

    // Constructor injection for UserRepository
    public IfExistUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(IfExistUserAnnotation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (id == null) {
            return false;
        }
        return userRepository.existsById(id);
    }
}
