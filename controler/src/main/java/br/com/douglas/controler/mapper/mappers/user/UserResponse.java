package br.com.douglas.controler.mapper.mappers.user;

import br.com.douglas.controler.mapper.mappers.authoritie.AuthorityResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;

    private boolean activated;

    Set<AuthorityResponse> authorities;
}