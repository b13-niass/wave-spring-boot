package com.example.wavespringboot.service.impl;

import com.example.wavespringboot.data.entity.User;
import com.example.wavespringboot.data.entity.Wallet;
import com.example.wavespringboot.data.repository.UserRepository;
import com.example.wavespringboot.data.repository.WalletRepository;
import com.example.wavespringboot.enums.EtatCompteEnum;
import com.example.wavespringboot.enums.RoleEnum;
import com.example.wavespringboot.exception.client.ClientNotFoundException;
import com.example.wavespringboot.service.AuthService;
import com.example.wavespringboot.service.MessageService;
import com.example.wavespringboot.web.dto.request.FindUserDTORequest;
import com.example.wavespringboot.web.dto.request.InscriptionClientDTORequest;
import com.example.wavespringboot.web.dto.request.LoginUserDTORequest;
import com.example.wavespringboot.web.dto.request.VerificationDTORequest;
import com.example.wavespringboot.web.dto.request.mapper.InscriptionClientDTOMapper;
import com.example.wavespringboot.web.dto.response.RegisterClientResponseDTO;
import com.example.wavespringboot.web.dto.response.VerificationDTOResponse;
import com.example.wavespringboot.web.dto.response.mapper.RegisterClientResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final RegisterClientResponseMapper registerClientResponseMapper;
    private final Authentication authentication;
    private final WalletRepository walletRepository;
    ;


    public AuthServiceImpl(@Qualifier("smsService") MessageService smsService,
                           @Qualifier("mailService") MessageService mailService,
                           BCryptPasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           InscriptionClientDTOMapper inscriptionClientDTOMapper,
                           SecureRandom secureRandom,
                           RegisterClientResponseMapper registerClientResponseMapper, WalletRepository walletRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.inscriptionClientDTOMapper = inscriptionClientDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.smsService = smsService;
        this.mailService = mailService;
        this.secureRandom = secureRandom;
        this.registerClientResponseMapper = registerClientResponseMapper;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        this.walletRepository = walletRepository;
    }

    public String generateUniqueCode() {
        int code = 1000 + secureRandom.nextInt(9000); // Génère un nombre entre 100000 et 999999
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
            user.setEtatCompte(EtatCompteEnum.ACTIF);
            user.setRole(RoleEnum.CLIENT);
//            smsService.sendMessage(user.getTelephone(),"Votre code de verification est : " + user.getCodeVerification());
            mailService.sendMailWithThymeleafAndQRCode(user.getEmail(), "Bienvenue sur Wave Mobile", user.getTelephone());
            user = this.userRepository.save(user);

            Wallet wallet = Wallet.builder().user(user).solde(0).plafond(200000).build();
            wallet = walletRepository.save(wallet);

            return user;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RegisterClientResponseDTO verifyCode(VerificationDTORequest verification) {

        User user = userRepository.findByCodeVerification(verification.code()).orElse(null);
        if (user!= null) {

            return registerClientResponseMapper.toDTO(user);
        }else {
            throw new ClientNotFoundException("Le Code de verification est incorrecte");
        }
    }

    @Override
    public RegisterClientResponseDTO findUserByTelephone(FindUserDTORequest findUserDTORequest) {
        System.out.println(findUserDTORequest.telephone());
        User user = userRepository.findByTelephone(findUserDTORequest.telephone()).orElse(null);
        if (user!= null) {
            user.setCodeVerification(generateUniqueCode());
//            smsService.sendMessage(user.getTelephone(), "Votre code de verification est : " + user.getCodeVerification());
            userRepository.save(user);
            return registerClientResponseMapper.toDTO(user);
        }else {
            throw new ClientNotFoundException("Vous n'avez pas encore créé de compte");
        }
    }

    @Override
    public User getAuthenticatedUser() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if (currentPrincipalName != null){
            user = userRepository.findByTelephone(currentPrincipalName).orElseThrow(
                    () -> new ClientNotFoundException("Utilisateur non trouvé")
            );
        }
        return user;
    }

}
