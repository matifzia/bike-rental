package com.atifzia.bikerental.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "bikes")
public class Bike {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String brand;
	private String model;
	private String registrationNumber;
	private String imageUrl;
	
	@OneToMany(mappedBy = "bike", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Booking> bookings;
}