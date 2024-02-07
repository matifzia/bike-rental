package com.atifzia.bikerental.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	@Qualifier("userServiceImpl")
	private UserDetailsService userService;

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}

		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = parseJwt(request);
		if (jwt != null && jwtProvider.validateJwtToken(jwt)) {
			String username = jwtProvider.getUserNameFromJwtToken(jwt);

			// Retrieve user details from your UserDetailsService
			UserDetails userDetails = userService.loadUserByUsername(username);

			// Create authentication token
			JwtAuthToken authToken = new JwtAuthToken(jwt, userDetails, userDetails.getAuthorities());
			authToken.setAuthenticated(true);
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//			authToken.setPrincipal(userDetails);

			// Set authentication in the context
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}

		filterChain.doFilter(request, response);
	}

}
