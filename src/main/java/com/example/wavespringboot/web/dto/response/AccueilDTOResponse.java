package com.example.wavespringboot.web.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class AccueilDTOResponse {
    private String qrCode;
    private UserDTOResponse user;
}
