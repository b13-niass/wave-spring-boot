package com.example.wavespringboot.service;

import com.example.wavespringboot.data.entity.User;
import com.example.wavespringboot.web.dto.request.InscriptionClientDTORequest;
import com.example.wavespringboot.web.dto.request.LoginUserDTORequest;

public interface AuthService {
    User authenticate(LoginUserDTORequest input);
    User registerClient(InscriptionClientDTORequest user);
}
