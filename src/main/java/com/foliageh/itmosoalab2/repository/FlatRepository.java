package com.foliageh.itmosoalab2.repository;

import com.foliageh.itmosoalab2.domain.flat.Flat;
import com.foliageh.itmosoalab2.domain.flat.Furnish;
import com.foliageh.itmosoalab2.domain.flat.Transport;
import jakarta.data.Order;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.Delete;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

@Repository
public interface FlatRepository extends BasicRepository<Flat, Integer> {
    @Query("SELECT f FROM Flat f WHERE (:name IS NULL OR LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:minArea IS NULL OR f.area >= :minArea) " +
            "AND (:maxArea IS NULL OR f.area <= :maxArea) " +
            "AND (:minRooms IS NULL OR f.number_of_rooms >= :minRooms) " +
            "AND (:maxRooms IS NULL OR f.number_of_rooms <= :maxRooms) " +
            "AND (:furnish IS NULL OR f.furnish = :furnish) " +
            "AND (:transport IS NULL OR f.transport = :transport)")
    Page<Flat> findAllByFilter(String name,
                               Integer minArea,
                               Integer maxArea,
                               Integer minRooms,
                               Integer maxRooms,
                               Furnish furnish,
                               Transport transport,
                               PageRequest pageRequest,
                               Order<Flat> order);

    @Delete
    long deleteByFurnish(Furnish furnish);

    @Query("SELECT f FROM Flat f WHERE f.number_of_rooms > :rooms")
    java.util.List<Flat> findByRoomsGreaterThan(int rooms);
}


