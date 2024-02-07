package com.atifzia.bikerental.services;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.atifzia.bikerental.configuration.JwtProvider;
import com.atifzia.bikerental.dtos.UserRequestDTO;
import com.atifzia.bikerental.enums.Role;
import com.atifzia.bikerental.models.User;
import com.atifzia.bikerental.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User registerUser(UserRequestDTO user) {
		if (userRepository.findByUsername(user.getUsername()) != null) {
			throw new RuntimeException("Username is already taken");
		}

		Set<Role> roles = new HashSet<>();
		roles.add(user.getRole());
		User userToRegister = User.builder().firstName(user.getFirstName()).lastName(user.getLastName())
				.email(user.getEmail()).mobile(user.getMobile()).roles(roles).username(user.getUsername())
				.password(passwordEncoder.encode(user.getPassword())).build();

		return userRepository.save(userToRegister);
	}

	@Override
	public String loginUser(String username, String password) {
		User user = userRepository.findByUsername(username);
		if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid username or password");
		} else {
			UserDetails userdet = loadUserByUsername(username);
			String token = jwtProvider.generateJwtToken(user.getUsername(), userdet.getAuthorities());
			return token;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

	@Transactional
	public Set<Role> getUserRoles(String username) {
		User user = userRepository.findByUsername(username);
		return user.getRoles();
	}
}
