package com.atifzia.bikerental.models;

import java.util.Set;
import com.atifzia.bikerental.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String email;
	private String mobile;
	@Column(unique = true)
	private String username;
	private String password;

	@ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
    private Set<Role> roles;
}