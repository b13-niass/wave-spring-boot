package com.example.wavespringboot.web.controller.impl;

import com.example.wavespringboot.data.entity.User;
import com.example.wavespringboot.service.impl.AuthServiceImpl;
import com.example.wavespringboot.service.impl.JwtServiceImpl;
import com.example.wavespringboot.web.controller.AuthController;
import com.example.wavespringboot.web.dto.request.FindUserDTORequest;
import com.example.wavespringboot.web.dto.request.InscriptionClientDTORequest;
import com.example.wavespringboot.web.dto.request.LoginUserDTORequest;
import com.example.wavespringboot.web.dto.request.VerificationDTORequest;
import com.example.wavespringboot.web.dto.response.LoginDTOResponse;
import com.example.wavespringboot.web.dto.response.RegisterClientResponseDTO;
import com.example.wavespringboot.web.dto.response.mapper.RegisterClientResponseMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthControllerImpl implements AuthController {
    private final JwtServiceImpl jwtService;
    private final AuthServiceImpl authenticationService;
    private final RegisterClientResponseMapper registerClientResponseMapper;

    public AuthControllerImpl(JwtServiceImpl jwtService, AuthServiceImpl authenticationService,  RegisterClientResponseMapper registerClientResponseMapper) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.registerClientResponseMapper = registerClientResponseMapper;

    }


    @PostMapping("/login")
    @Override
    public ResponseEntity<LoginDTOResponse> authenticate(@RequestBody LoginUserDTORequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginDTOResponse loginResponse = LoginDTOResponse.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build();

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/inscription/client")
    @Override
    public ResponseEntity<RegisterClientResponseDTO> registerClient(@RequestBody @Valid InscriptionClientDTORequest inscription) {
        User user = authenticationService.registerClient(inscription);
        return ResponseEntity.ok(registerClientResponseMapper.toDTO(user));
    }

    @PostMapping("/find-by-telephone")
    @Override
    public ResponseEntity<RegisterClientResponseDTO> findUserByTelephone(FindUserDTORequest findUserDTORequest) {
        return ResponseEntity.ok(authenticationService.findUserByTelephone(findUserDTORequest));
    }

    @PostMapping("/verify-code")
    @Override
    public ResponseEntity<RegisterClientResponseDTO> verifyCode(VerificationDTORequest verification) {
        return ResponseEntity.ok(authenticationService.verifyCode(verification));
    }
}
