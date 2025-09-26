package com.foliageh.itmosoalab2.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

@Value
public class HouseDto {
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


