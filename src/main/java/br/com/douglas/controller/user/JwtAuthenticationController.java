package br.com.douglas.controller.user;

import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.mappers.JWTTokenResponse;
import br.com.douglas.mapper.mappers.user.UserRequest;
import br.com.douglas.security.config.JwtTokenUtil;
import br.com.douglas.security.service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class JwtAuthenticationController {


	private final JwtTokenUtil jwtTokenUtil;
	private final AuthenticationService authenticationService;

	public JwtAuthenticationController(JwtTokenUtil jwtTokenUtil,
									   AuthenticationService authenticationService) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JWTTokenResponse> createAuthenticationToken(@RequestBody UserRequest userRequest) throws DomainException {

		var response = authenticationService.authenticate(userRequest);

		final String token = jwtTokenUtil.generateToken(response.getUsername());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + token);

		return new ResponseEntity<>(new JWTTokenResponse(token, response), httpHeaders, HttpStatus.OK);
	}


}