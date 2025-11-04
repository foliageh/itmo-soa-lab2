package com.foliageh.itmosoalab2.remote;

import com.foliageh.itmosoalab2.api.dto.FlatDto;
import com.foliageh.itmosoalab2.api.dto.FlatPageResponse;
import com.foliageh.itmosoalab2.api.dto.FlatsFilterDto;
import com.foliageh.itmosoalab2.domain.flat.Flat;
import com.foliageh.itmosoalab2.domain.flat.Furnish;
import jakarta.ejb.Remote;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

@Remote
public interface FlatServiceRemote extends BaseServiceRemote {

    Flat create(@Valid FlatDto dto);

    Flat get(int id);

    Flat update(int id, @Valid FlatDto dto);

    void delete(int id);

    FlatPageResponse filter(FlatsFilterDto filter,
                            String sortBy,
                            String sortDirection,
                            @Min(0) int pageNumber,
                            @Min(1) int pageSize);

    List<Flat> findRoomsGreaterThan(int rooms);

    void deleteByFurnish(Furnish furnish);

    List<Double> getUniqueLivingSpaces();

    boolean launchUniqueLivingSpacesJob();

    boolean cancelUniqueLivingSpacesJob();
}
