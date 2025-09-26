package com.foliageh.itmosoalab2.domain.flat;

import com.foliageh.itmosoalab2.api.dto.FlatDto;

public final class FlatMapper {
    private FlatMapper() {}

    public static Flat toEntity(FlatDto dto) {
        return Flat.builder()
                .name(dto.getName())
                .coordinates(Coordinates.builder().x(dto.getCoordinates().getX()).y(dto.getCoordinates().getY()).build())
                .area(dto.getArea())
                .number_of_rooms(dto.getNumber_of_rooms())
                .living_space(dto.getLiving_space())
                .furnish(dto.getFurnish())
                .transport(dto.getTransport())
                .house(House.builder()
                        .name(dto.getHouse().getName())
                        .year(dto.getHouse().getYear())
                        .number_of_floors(dto.getHouse().getNumber_of_floors())
                        .number_of_lifts(dto.getHouse().getNumber_of_lifts())
                        .build())
                .build();
    }

    public static Flat apply(Flat target, FlatDto dto) {
        target.setName(dto.getName());
        target.setCoordinates(Coordinates.builder().x(dto.getCoordinates().getX()).y(dto.getCoordinates().getY()).build());
        target.setArea(dto.getArea());
        target.setNumber_of_rooms(dto.getNumber_of_rooms());
        target.setLiving_space(dto.getLiving_space());
        target.setFurnish(dto.getFurnish());
        target.setTransport(dto.getTransport());
        target.setHouse(House.builder()
                .name(dto.getHouse().getName())
                .year(dto.getHouse().getYear())
                .number_of_floors(dto.getHouse().getNumber_of_floors())
                .number_of_lifts(dto.getHouse().getNumber_of_lifts())
                .build());
        return target;
    }
}


