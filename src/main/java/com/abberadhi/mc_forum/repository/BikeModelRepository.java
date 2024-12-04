package com.abberadhi.mc_forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abberadhi.mc_forum.model.BikeModelEntity;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

@Repository
public interface BikeModelRepository extends JpaRepository<BikeModelEntity, Long> {

    long count();

    @Query("select distinct brand from BikeModelEntity")
    List<String> findBrands();

    List<BikeModelEntity> findByBrand(String brand);

}
