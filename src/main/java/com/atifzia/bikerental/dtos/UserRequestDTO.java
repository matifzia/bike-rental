package com.atifzia.bikerental.dtos;

import com.atifzia.bikerental.enums.Role;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String firstName;
	private String lastName;
	private String email;
	private String mobile;
	private String username;
	private String password;
    private Role role;
}
