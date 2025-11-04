package com.foliageh.itmosoalab2.domain.flat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter(value = AccessLevel.PACKAGE)
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class House implements Serializable {
    @NotBlank
    @Column(columnDefinition = "TEXT")
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


