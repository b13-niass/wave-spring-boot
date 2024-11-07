package com.example.wavespringboot.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class VerificationDTOResponse {
    private boolean valid;
}
