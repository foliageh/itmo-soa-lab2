package com.foliageh.itmosoalab2.api.dto;

import com.foliageh.itmosoalab2.domain.flat.Furnish;
import com.foliageh.itmosoalab2.domain.flat.Transport;
import lombok.Value;

@Value
public class FlatsFilterDto {
    String name;
    Integer min_area;
    Integer max_area;
    Integer min_rooms;
    Integer max_rooms;
    Integer min_price;
    Integer max_price;
    Furnish furnish;
    Transport transport;
    Boolean has_balcony;
}


