package com.example.wavespringboot.service.impl;

import com.example.wavespringboot.data.entity.User;
import com.example.wavespringboot.data.repository.UserRepository;
import com.example.wavespringboot.service.AuthService;
import com.example.wavespringboot.service.MessageService;
import com.example.wavespringboot.web.dto.request.InscriptionClientDTORequest;
import com.example.wavespringboot.web.dto.request.LoginUserDTORequest;
import com.example.wavespringboot.web.dto.request.mapper.InscriptionClientDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final InscriptionClientDTOMapper inscriptionClientDTOMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MessageService smsService;
    private final MessageService mailService;
    private final SecureRandom secureRandom;

    public AuthServiceImpl(@Qualifier("smsService") MessageService smsService,
                           @Qualifier("mailService") MessageService mailService,
                           BCryptPasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           InscriptionClientDTOMapper inscriptionClientDTOMapper,
                           SecureRandom secureRandom) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.inscriptionClientDTOMapper = inscriptionClientDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.smsService = smsService;
        this.mailService = mailService;
        this.secureRandom = secureRandom;
    }

    public String generateUniqueCode() {
        int code = 100000 + secureRandom.nextInt(900000); // Génère un nombre entre 100000 et 999999
        return String.valueOf(code);
    }

    public User authenticate(LoginUserDTORequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.telephone(),
                        input.password()
                )
        );

        return userRepository.findByTelephone(input.telephone())
                .orElseThrow();
    }

    @Override
    public User registerClient(InscriptionClientDTORequest inscription) {
        try {
            User user = this.inscriptionClientDTOMapper.toEntity(inscription);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCodeVerification(generateUniqueCode());
            smsService.sendMessage(user.getTelephone(), "Votre code de verification est : " + user.getCodeVerification());
            mailService.sendMailWithThymeleafAndQRCode(user.getEmail(), "Bienvenue sur Wave Mobile", user.getTelephone());
            return this.userRepository.save(user);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
