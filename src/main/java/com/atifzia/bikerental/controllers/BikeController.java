package com.atifzia.bikerental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.atifzia.bikerental.dtos.BookingDTO;
import com.atifzia.bikerental.models.Bike;
import com.atifzia.bikerental.models.User;
import com.atifzia.bikerental.repositories.UserRepository;
import com.atifzia.bikerental.services.BikeService;
import java.util.List;

@RestController
@RequestMapping("/api/bikes")
public class BikeController {
	@Autowired
	private BikeService bikeService;
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addBike(@RequestBody Bike bike) {
		bikeService.addBike(bike);
		return ResponseEntity.ok("Bike added successfully");
	}

	@GetMapping("/list")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<List<Bike>> getAllBikes(Authentication authentication) {
		System.out.print("AUTHS :" + authentication.getAuthorities());
		System.out.print("DETS :" + authentication);
		List<Bike> bikes = bikeService.getAllBikes();
		return ResponseEntity.ok(bikes);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/booking/{userId}")
	public ResponseEntity<List<BookingDTO>> getUserBookings(@PathVariable("userId") Long userId) {
		List<BookingDTO> bookings = bikeService.bookingsByUserId(userId);
		return ResponseEntity.ok(bookings);
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/booking/{bikeId}")
	public ResponseEntity<String> bookABike(@PathVariable("bikeId") Long bikeId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		org.springframework.security.core.userdetails.User authenticatedUser = (org.springframework.security.core.userdetails.User) authentication
				.getPrincipal();
		User user = userRepository.findByUsername(authenticatedUser.getUsername());
		bikeService.bookABike(user, bikeId);
		return ResponseEntity.ok("New Booking successful");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/booking/list")
	public ResponseEntity<List<BookingDTO>> getAllBookings() {
		List<BookingDTO> bookings = bikeService.allBookings();
		return ResponseEntity.ok(bookings);
	}
}
