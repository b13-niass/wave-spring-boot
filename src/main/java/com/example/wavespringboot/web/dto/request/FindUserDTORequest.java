package com.example.wavespringboot.web.dto.request;

import lombok.Builder;

@Builder(toBuilder = true)
public record FindUserDTORequest(
        String telephone
) {
}
