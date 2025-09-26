package com.foliageh.itmosoalab2.service;

import com.foliageh.itmosoalab2.api.dto.FlatDto;
import com.foliageh.itmosoalab2.api.dto.FlatPageResponse;
import com.foliageh.itmosoalab2.api.dto.FlatsFilterDto;
import com.foliageh.itmosoalab2.domain.flat.Flat;
import com.foliageh.itmosoalab2.domain.flat.Furnish;
import com.foliageh.itmosoalab2.domain.flat.FlatMapper;
import com.foliageh.itmosoalab2.repository.FlatRepository;
import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class FlatService {
    @Inject
    FlatRepository repository;

    public Flat create(@Valid FlatDto dto) {
        Flat entity = FlatMapper.toEntity(dto);
        return repository.save(entity);
    }

    public Flat get(int id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Flat update(int id, @Valid FlatDto dto) {
        Flat existing = repository.findById(id).orElseThrow(NotFoundException::new);
        Flat updated = FlatMapper.apply(existing, dto);
        return repository.save(updated);
    }

    public void delete(int id) {
        Flat existing = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.delete(existing);
    }

    public FlatPageResponse filter(FlatsFilterDto filter,
                                   String sortBy,
                                   String sortDirection,
                                   @Min(0) int pageNumber,
                                   @Min(1) int pageSize) {
        String sortField = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
        Sort<Flat> sort = "desc".equalsIgnoreCase(sortDirection) ? Sort.desc(sortField) : Sort.asc(sortField);

        PageRequest pageRequest = PageRequest.ofPage(pageNumber + 1).size(pageSize);
        Page<Flat> page = repository.findAllByFilter(
                filter.getName(),
                filter.getMin_area(),
                filter.getMax_area(),
                filter.getMin_rooms(),
                filter.getMax_rooms(),
                filter.getFurnish(),
                filter.getTransport(),
                pageRequest,
                Order.by(sort)
        );

        return FlatPageResponse.builder()
                .flats(page.content())
                .pageNumber(pageNumber)
                .pageNumberOfElements(page.numberOfElements())
                .totalPages((int) page.totalPages())
                .numberOfElements((int) page.totalElements())
                .build();
    }

    public List<Flat> findRoomsGreaterThan(int rooms) {
        return repository.findByRoomsGreaterThan(rooms);
    }

    public void deleteByFurnish(Furnish furnish) {
        repository.deleteByFurnish(furnish);
    }

    public List<Double> getUniqueLivingSpaces() {
        return java.util.Collections.emptyList();
    }

    public void launchUniqueLivingSpacesJob() {
    }

    public void cancelUniqueLivingSpacesJob() {
    }
}


