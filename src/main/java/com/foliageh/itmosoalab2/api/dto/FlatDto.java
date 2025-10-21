package com.foliageh.itmosoalab2.api.dto;

import com.foliageh.itmosoalab2.domain.flat.Furnish;
import com.foliageh.itmosoalab2.domain.flat.Transport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class FlatDto {
    @NotBlank
    String name;

    @NotNull
    @Valid
    CoordinatesDto coordinates;

    @NotNull
    @Min(1)
    @Max(784)
    Integer area;

    @NotNull
    @Min(1)
    @Max(8)
    Integer number_of_rooms;

    @NotNull
    Double living_space;

    @NotNull
    Double price;

    @NotNull
    boolean has_balcony;

    Furnish furnish;

    @NotNull
    Transport transport;

    @NotNull
    @Valid
    HouseDto house;
}


