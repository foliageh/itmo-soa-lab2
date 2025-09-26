package com.foliageh.itmosoalab2.domain.flat;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter(value = AccessLevel.PACKAGE)
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class House {
    private String name;

    @NotNull
    @Min(1)
    @Max(370)
    private Long year;

    @NotNull
    @Positive
    private Integer number_of_floors;

    @NotNull
    @Positive
    private Integer number_of_lifts;
}


