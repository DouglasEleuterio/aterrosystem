package br.com.douglas.mapper.mappers.user;

import br.com.douglas.mapper.mappers.authoritie.AuthorityRequest;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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

    private boolean activated;
}
