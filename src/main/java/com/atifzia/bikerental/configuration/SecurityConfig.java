package com.atifzia.bikerental.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.*;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
			new AntPathRequestMatcher("/api/users/register"), new AntPathRequestMatcher("/api/users/login"),
			new AntPathRequestMatcher("/h2-console/**"));

	private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);

	@Bean
	public JwtAuthTokenFilter authenticationJwtTokenFilter() {
		return new JwtAuthTokenFilter();
	}

//	@Bean
//	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//	static Advisor preAuthorizeMethodInterceptor() {
//	    return AuthorizationManagerBeforeMethodInterceptor.preAuthorize();
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable).headers(headers -> headers.frameOptions().sameOrigin())
				.authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_URLS).permitAll()
						.requestMatchers(PROTECTED_URLS).authenticated()
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(withDefaults())
				.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
