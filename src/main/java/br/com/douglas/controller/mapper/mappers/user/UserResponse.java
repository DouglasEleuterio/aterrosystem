package br.com.douglas.controller.mapper.mappers.user;

import br.com.douglas.controller.mapper.mappers.authoritie.AuthorityResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;

    private boolean activated;

    Set<AuthorityResponse> authorities;
}