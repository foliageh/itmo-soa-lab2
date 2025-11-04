package com.foliageh.itmosoalab2.api.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.io.Serializable;

@Value
public class HouseDto implements Serializable {
    @NotBlank
    String name;

    @NotNull
    @Min(1)
    @Max(370)
    Long year;

    @NotNull
    @Positive
    Integer number_of_floors;

    @NotNull
    @Positive
    Integer number_of_lifts;
}


