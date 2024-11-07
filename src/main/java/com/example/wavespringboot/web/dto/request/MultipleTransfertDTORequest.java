package com.example.wavespringboot.web.dto.request;

import jakarta.validation.Valid;

import java.util.List;

public record MultipleTransfertDTORequest(
        @Valid
        List<TransfertDTORequest> transferts
) {
}
