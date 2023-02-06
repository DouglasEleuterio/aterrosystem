package br.com.douglas.controller.user;

import br.com.douglas.controller.mapper.mappers.JWTTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import br.com.douglas.controller.mapper.mappers.LoginDto;
import br.com.douglas.security.jwt.JWTFilter;
import br.com.douglas.security.jwt.TokenProvider;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class AuthenticationRestController {

   private final TokenProvider tokenProvider;

   private final AuthenticationManagerBuilder authenticationManagerBuilder;

   public AuthenticationRestController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
      this.tokenProvider = tokenProvider;
      this.authenticationManagerBuilder = authenticationManagerBuilder;
   }

   @GetMapping("/token")
   public ResponseEntity<JWTTokenResponse> refreshAuthenticationToken(Authentication authentication) {

      String jwt = tokenProvider.createToken(authentication, false);

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

      return new ResponseEntity<>(new JWTTokenResponse(jwt), httpHeaders, HttpStatus.OK);

   }

   @PostMapping("/authenticate")
   public ResponseEntity<JWTTokenResponse> authorize(@Valid @RequestBody LoginDto loginDto) {

      UsernamePasswordAuthenticationToken authenticationToken =
         new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

      Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);

      String jwt = tokenProvider.createToken(authentication, loginDto.isRememberMe());

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

      return new ResponseEntity<>(new JWTTokenResponse(jwt), httpHeaders, HttpStatus.OK);
   }
}