package com.atifzia.bikerental.dtos;

import lombok.Data;

@Data
public class UserResponseDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobile;
}
