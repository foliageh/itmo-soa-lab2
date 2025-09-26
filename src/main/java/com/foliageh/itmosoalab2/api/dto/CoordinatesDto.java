package com.foliageh.itmosoalab2.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CoordinatesDto {
    @NotNull
    @DecimalMin(value = "-12.999", inclusive = true)
    Long x;

    @NotNull
    @DecimalMin(value = "-732.999", inclusive = true)
    Float y;
}


