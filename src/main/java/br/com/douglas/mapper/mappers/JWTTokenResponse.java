package br.com.douglas.mapper.mappers;

import br.com.douglas.mapper.mappers.user.UserResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JWTTokenResponse {

   @JsonProperty("id_token")
   private String idToken;

   @JsonProperty("auth-user")
   private Object user;
}
