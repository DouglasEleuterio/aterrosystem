package br.com.douglas.controller.mapper.mappers.user;

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
