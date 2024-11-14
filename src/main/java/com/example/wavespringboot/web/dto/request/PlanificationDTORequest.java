package com.example.wavespringboot.web.dto.request;

import com.example.wavespringboot.enums.RecurrenceType;
import com.example.wavespringboot.validator.annotation.IfExistUserAnnotation;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder(toBuilder = true)
public record PlanificationDTORequest(
        @Positive(message = "Le montant doit être supérieur à 0")
        Double montant,
        RecurrenceType recurrenceType,
        String timeOfDay,
        String daysOfWeek,
        Integer dayOfMonth,
        @IfExistUserAnnotation
        String telephone
) {
}
