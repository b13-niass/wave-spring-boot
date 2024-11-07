package com.example.wavespringboot.service;

import com.example.wavespringboot.data.entity.User;
import com.example.wavespringboot.web.dto.request.FindUserDTORequest;
import com.example.wavespringboot.web.dto.request.InscriptionClientDTORequest;
import com.example.wavespringboot.web.dto.request.LoginUserDTORequest;
import com.example.wavespringboot.web.dto.request.VerificationDTORequest;
import com.example.wavespringboot.web.dto.response.RegisterClientResponseDTO;

public interface AuthService {
    User authenticate(LoginUserDTORequest input);
    User registerClient(InscriptionClientDTORequest user);
    RegisterClientResponseDTO verifyCode(VerificationDTORequest verification);
    RegisterClientResponseDTO findUserByTelephone(FindUserDTORequest telephone);
}
