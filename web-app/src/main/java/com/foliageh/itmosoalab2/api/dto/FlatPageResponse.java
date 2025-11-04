package com.foliageh.itmosoalab2.api.dto;

import com.foliageh.itmosoalab2.domain.flat.Flat;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
@Builder
public class FlatPageResponse implements Serializable {
    List<Flat> flats;

    @Min(0)
    Integer pageNumber;

    Integer pageNumberOfElements;

    Integer totalPages;

    Integer numberOfElements;
}


