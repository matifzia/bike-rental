package com.atifzia.bikerental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.atifzia.bikerental.models.Bike;

public interface BikeRepository extends JpaRepository<Bike, Long> {

}