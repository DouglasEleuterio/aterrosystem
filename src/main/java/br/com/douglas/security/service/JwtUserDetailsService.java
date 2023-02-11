package br.com.douglas.security.service;

import br.com.douglas.mapper.mappers.user.UserMapper;
import br.com.douglas.mapper.mappers.user.UserRequest;
import br.com.douglas.mapper.mappers.user.UserResponse;
import br.com.douglas.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder bcryptEncoder;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public JwtUserDetailsService(UserRepository userRepository,
								 PasswordEncoder bcryptEncoder,
								 UserMapper userMapper,
								 PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.bcryptEncoder = bcryptEncoder;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		return new User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(this).passwordEncoder(passwordEncoder);
	}

	public UserResponse save(UserRequest user) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		user.setActivated(true);
		return userMapper.toResponse(userRepository.save(userMapper.fromRequest(user)));
	}
}