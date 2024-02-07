package com.atifzia.bikerental.services;

import java.util.List;
import com.atifzia.bikerental.dtos.BookingDTO;
import com.atifzia.bikerental.models.Bike;
import com.atifzia.bikerental.models.Booking;
import com.atifzia.bikerental.models.User;

public interface BikeService {
	Bike addBike(Bike bike);
	List<Bike> getAllBikes();
	Bike getBikeById(Long id);
	Booking bookABike(User user, Long bike);
	List<BookingDTO> allBookings();
	List<BookingDTO> bookingsByUserId(Long userId);
}
