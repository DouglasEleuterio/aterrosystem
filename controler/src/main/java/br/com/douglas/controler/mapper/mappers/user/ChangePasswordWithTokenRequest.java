package br.com.douglas.controler.mapper.mappers.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class ChangePasswordWithTokenRequest {

    private String token;
    private String username;
    private String newPassword;
}
