package com.foliageh.itmosoalab2.domain.flat;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class Coordinates {
    @NotNull
    @DecimalMin(value = "-13", inclusive = false)
    private Long x;

    @NotNull
    @DecimalMin(value = "-733", inclusive = false)
    private Float y;
}


