package com.example.wavespringboot.web.controller;

import com.example.wavespringboot.web.dto.request.InscriptionClientDTORequest;
import com.example.wavespringboot.web.dto.request.LoginUserDTORequest;
import com.example.wavespringboot.web.dto.response.LoginDTOResponse;
import com.example.wavespringboot.web.dto.response.RegisterClientResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AuthController {
    ResponseEntity<LoginDTOResponse> authenticate(LoginUserDTORequest loginUserDto);
    ResponseEntity<RegisterClientResponseDTO> registerClient(InscriptionClientDTORequest inscription);
}