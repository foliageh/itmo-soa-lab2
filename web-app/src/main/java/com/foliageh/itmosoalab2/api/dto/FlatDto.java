package com.foliageh.itmosoalab2.api.dto;

import com.foliageh.itmosoalab2.domain.flat.Furnish;
import com.foliageh.itmosoalab2.domain.flat.Transport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Value;

import java.io.Serializable;

@Value
public class FlatDto implements Serializable {
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
    @Positive
    Double living_space;

    @NotNull
    @Min(1)
    Integer price;

    @NotNull
    Boolean has_balcony;

    Furnish furnish;

    @NotNull
    Transport transport;

    @NotNull
    @Valid
    HouseDto house;
}


