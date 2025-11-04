package com.foliageh.itmosoalab2.repository;

import com.foliageh.itmosoalab2.domain.flat.Flat;
import com.foliageh.itmosoalab2.domain.flat.Furnish;
import com.foliageh.itmosoalab2.domain.flat.Transport;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@Repository
public interface FlatRepository extends BasicRepository<Flat, Integer> {
    @Query("WHERE (:_name IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%', :_name, '%'))) " +
            "AND (:_minArea IS NULL OR area >= CAST(:_minArea AS INTEGER)) " +
            "AND (:_maxArea IS NULL OR area <= CAST(:_maxArea AS INTEGER)) " +
            "AND (:_minRooms IS NULL OR number_of_rooms >= CAST(:_minRooms AS INTEGER)) " +
            "AND (:_maxRooms IS NULL OR number_of_rooms <= CAST(:_maxRooms AS INTEGER)) " +
            "AND (:_minPrice IS NULL OR price >= CAST(:_minPrice AS INTEGER)) " +
            "AND (:_maxPrice IS NULL OR price <= CAST(:_maxPrice AS INTEGER)) " +
            "AND (:_furnish IS NULL OR furnish = :_furnish) " +
            "AND (:_transport IS NULL OR transport = :_transport) " +
            "AND (:_hasBalcony IS NULL OR has_balcony = CAST(:_hasBalcony AS BOOLEAN)) ")
    Page<Flat> findAllByFilter(String _name,
                               Integer _minArea,
                               Integer _maxArea,
                               Integer _minRooms,
                               Integer _maxRooms,
                               Integer _minPrice,
                               Integer _maxPrice,
                               Furnish _furnish,
                               Transport _transport,
                               Boolean _hasBalcony,
                               PageRequest pageRequest,
                               Order<Flat> order);

    @Query("DELETE FROM Flat where furnish = ?1")
    void deleteByFurnish(Furnish furnish);

    @Query("WHERE number_of_rooms > :_rooms")
    List<Flat> findByRoomsGreaterThan(int _rooms);

    @Query("SELECT DISTINCT living_space FROM Flat")
    List<Double> findUniqueLivingSpaces();
}


