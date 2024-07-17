package com.company.books.backend.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigSecurity {

	/*
	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		UserDetails miguel = User.builder()
				.username("miguel")
				.password("{noop}miguel123")
				.roles("Empleado")
				.build();

		UserDetails agustin = User.builder()
				.username("agustin")
				.password("{noop}agustin123")
				.roles("Empleado", "Jefe")
				.build();

		UserDetails edita = User.builder()
				.username("edita")
				.password("{noop}edita123")
				.roles("Empleado", "Jefe")
				.build();

		return new InMemoryUserDetailsManager(miguel, agustin, edita);
	}*/
	
	@Bean
	public UserDetailsManager userDetailsManager(DataSource datasource) {
		return new JdbcUserDetailsManager(datasource);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(configure -> {
			configure
			.requestMatchers(HttpMethod.GET, "/v1/libros").hasRole("Empleado")
			.requestMatchers(HttpMethod.GET, "/v1/libros/**").hasRole("Empleado")
			.requestMatchers(HttpMethod.POST, "/v1/libros").hasRole("Jefe")
			.requestMatchers(HttpMethod.PUT, "/v1/libros/**").hasRole("Jefe")
			.requestMatchers(HttpMethod.DELETE, "/v1/libros/**").hasRole("Jefe")
			.requestMatchers(HttpMethod.GET, "/v1/categorias").hasRole("Empleado")
			.requestMatchers(HttpMethod.GET, "/v1/categorias/**").hasRole("Empleado")
			.requestMatchers(HttpMethod.POST, "/v1/categorias").hasRole("Jefe")
			.requestMatchers(HttpMethod.PUT, "/v1/categorias/**").hasRole("Jefe")
			.requestMatchers(HttpMethod.DELETE, "/v1/libros/**").hasRole("Jefe");
		});
		
		http.httpBasic(Customizer.withDefaults());
		http.csrf(csrf -> csrf.disable());

		return http.build();
	}

}
