package com.foliageh.itmosoalab2.service;

import com.foliageh.itmosoalab2.api.dto.FlatDto;
import com.foliageh.itmosoalab2.api.dto.FlatPageResponse;
import com.foliageh.itmosoalab2.api.dto.FlatsFilterDto;
import com.foliageh.itmosoalab2.domain.flat.Flat;
import com.foliageh.itmosoalab2.domain.flat.FlatMapper;
import com.foliageh.itmosoalab2.domain.flat.Furnish;
import com.foliageh.itmosoalab2.repository.FlatRepository;
import jakarta.data.Order;
import jakarta.data.Sort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class FlatService {
    @Inject
    FlatRepository repository;
    
    @Inject
    AsyncTaskService asyncTaskService;

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
        Sort<Flat>[] sorts;
        String[] fields = sortBy == null ? new String[]{} : sortBy.split(",");
        String[] directions = sortDirection == null ? new String[]{} : sortDirection.split(",");
        if (directions.length != fields.length)
            throw new BadRequestException("Количество sortDirection должно совпадать с количеством sortBy");

        if (fields.length == 0) {
            sorts = new Sort[]{ Sort.asc("id") };
        } else {
            sorts = new Sort[fields.length];
            for (int i = 0; i < fields.length; i++) {
                String field = fields[i].toLowerCase();
                String direction = directions[i].toLowerCase();
                if (field.isEmpty() || direction.isEmpty())
                    throw new BadRequestException("Пустое поле или направление сортировки не разрешено");
                if (!"asc".equals(direction) && !"desc".equals(direction))
                    throw new BadRequestException("Некорректное направление сортировки: " + direction);
                sorts[i] = "asc".equals(direction) ? Sort.asc(field) : Sort.desc(field);
            }
        }

        PageRequest pageRequest = PageRequest.ofPage(pageNumber + 1).size(pageSize);
        Page<Flat> page = repository.findAllByFilter(
                filter.getName(),
                filter.getMin_area(),
                filter.getMax_area(),
                filter.getMin_rooms(),
                filter.getMax_rooms(),
                filter.getMin_price(),
                filter.getMax_price(),
                filter.getFurnish(),
                filter.getTransport(),
                filter.getHas_balcony(),
                pageRequest,
                Order.by(sorts)
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
        if (asyncTaskService.isTaskInProgress())
            return null;
        if (asyncTaskService.isTaskCompleted())
            return asyncTaskService.getTaskResult();
        throw new NotFoundException("Задача не найдена");
    }

    public boolean launchUniqueLivingSpacesJob() {
        return asyncTaskService.launchUniqueLivingSpacesJob();
    }

    public boolean cancelUniqueLivingSpacesJob() {
        return asyncTaskService.cancelUniqueLivingSpacesJob();
    }
    
    public List<Double> getUniqueLivingSpacesFromDatabase() {
        return repository.findUniqueLivingSpaces();
    }
}


