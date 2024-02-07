package com.atifzia.bikerental.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.atifzia.bikerental.models.Booking;

public interface BikeBookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUserId(Long id);
}