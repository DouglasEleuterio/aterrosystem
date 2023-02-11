package br.com.douglas.mapper.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091865418191989844L;

    private String jwttoken;
}
