package com.atifzia.bikerental.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import com.atifzia.bikerental.dtos.BookingDTO;
import com.atifzia.bikerental.dtos.UserResponseDTO;
import com.atifzia.bikerental.models.Bike;
import com.atifzia.bikerental.models.Booking;
import com.atifzia.bikerental.models.User;
import com.atifzia.bikerental.repositories.BikeBookingRepository;
import com.atifzia.bikerental.repositories.BikeRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BikeServiceImpl implements BikeService {
	@Autowired
	private BikeRepository bikeRepository;
	@Autowired
	private BikeBookingRepository bikeBookingRepository;

	@Override
//	@PreAuthorize("hasRole('USER')")
	public Bike addBike(Bike bike) {
		return bikeRepository.save(bike);
	}

	@Override
//	@PreAuthorize("hasRole('ROLE_USER')")
	public List<Bike> getAllBikes() {
		return bikeRepository.findAll();
	}

	@Override
	public Bike getBikeById(Long id) {
		return bikeRepository.findById(id).get();
	}

	@Override
	public Booking bookABike(User user, Long bikeId) {
		Optional<Bike> bike = bikeRepository.findById(bikeId);
		if (bike.isPresent()) {
			Booking newBooking = Booking.builder().user(user).bike(bike.get()).build();
			return bikeBookingRepository.save(newBooking);
		} else {
			throw new RuntimeException("Bike not exist");
		}
	}

	@Override
	public List<BookingDTO> allBookings() {
		List<Booking> bookings = bikeBookingRepository.findAll();
		return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public List<BookingDTO> bookingsByUserId(Long userId) {
		List<Booking> bookings = bikeBookingRepository.findByUserId(userId);
		return bookings.stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	private BookingDTO convertToDTO(Booking booking) {
		BookingDTO bookingDTO = new BookingDTO();
		bookingDTO.setId(booking.getId());
		bookingDTO.setBike(booking.getBike());
		bookingDTO.setCreatedDate(booking.getCreatedDate());
		User bookingUser=booking.getUser();
		UserResponseDTO user=new UserResponseDTO();
		user.setFirstName(bookingUser.getFirstName());
		user.setLastName(bookingUser.getLastName());
		user.setEmail(bookingUser.getEmail());
		user.setId(bookingUser.getId());
		user.setMobile(bookingUser.getMobile());
		bookingDTO.setBookedBy(user);
		return bookingDTO;
	}
}
