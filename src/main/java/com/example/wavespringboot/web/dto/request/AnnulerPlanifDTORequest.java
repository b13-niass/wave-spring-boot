package com.example.wavespringboot.web.dto.request;

import lombok.Builder;

@Builder(toBuilder = true)
public record AnnulerPlanifDTORequest(
        Long id
) {
}
