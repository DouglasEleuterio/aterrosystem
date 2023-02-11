package br.com.douglas.security.service;

import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.mapper.mappers.user.UserMapper;
import br.com.douglas.mapper.mappers.user.UserRequest;
import br.com.douglas.mapper.mappers.user.UserResponse;
import br.com.douglas.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthenticationService(AuthenticationManager authenticationManager, UserService userService, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public UserResponse  authenticate(UserRequest userRequest) throws DomainException {
        try {
            var authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return userMapper.toResponse(userService.findByUsername(authentication.getName()));
        } catch (DisabledException e) {
            throw new SecurityException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new SecurityException("INVALID_CREDENTIALS", e);
        }
    }
}
