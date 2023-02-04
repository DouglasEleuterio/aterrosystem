package br.com.douglas.controler.mapper.mappers.user;

import br.com.douglas.controler.mapper.mappers.authoritie.AuthorityRequest;
import br.com.douglas.entity.entities.Authority;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class UserRequest {

    @Length(min = 5, max = 30)
    private String username;

    @Length(min = 8, max = 30)
    private String password;
    private String firstname;
    private String lastname;
    private String email;

    Set<AuthorityRequest> authorities;
}
