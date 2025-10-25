package com.foliageh.itmosoalab2.domain.flat;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter(value = AccessLevel.PACKAGE)
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private LocalDate creationDate;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String name;

    @NotNull
    @Valid
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "coord_x", nullable = false)),
            @AttributeOverride(name = "y", column = @Column(name = "coord_y", nullable = false))
    })
    @Embedded
    private Coordinates coordinates;

    @NotNull
    @Min(1)
    @Max(784)
    private Integer area;

    @NotNull
    @Min(1)
    @Max(8)
    @Column
    private Integer number_of_rooms;

    @NotNull
    @Positive
    private Double living_space;

    @NotNull
    @Min(1)
    private Integer price;

    @NotNull
    private Boolean has_balcony;

    @Enumerated(EnumType.STRING)
    private Furnish furnish;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Transport transport;

    @NotNull
    @Valid
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "house_name")),
    })
    @Embedded
    private House house;

    @PrePersist
    void prePersist() {
        if (creationDate == null) {
            creationDate = LocalDate.now();
        }
    }
}


