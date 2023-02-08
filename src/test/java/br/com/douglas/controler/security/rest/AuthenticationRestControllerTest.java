package br.com.douglas.controler.security.rest;

import br.com.douglas.controler.util.BaseTest;
import br.com.douglas.controler.util.LogInUtils;
import br.com.douglas.controller.mapper.mappers.JWTTokenResponse;
import br.com.douglas.controller.mapper.mappers.authoritie.AuthorityRequest;
import br.com.douglas.controller.mapper.mappers.user.UserRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static br.com.douglas.controler.util.LogInUtils.getTokenForLogin;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationRestControllerTest extends BaseTest {

    private final String AUTHENTICATE_URL = "authenticate";
    private final String AUTHORITY_URL = "authority";
    private static String idUsuario = "";
    private String idRule = "";

    @Before
    public void setUp() throws Exception {
        Map<String, String> objectObjectMap = salvarUsuario();
        for (Map.Entry<String, String> obj : objectObjectMap.entrySet()) {
            idUsuario = obj.getKey();
            idRule = obj.getValue();
        }
    }


    @After
    public void deletarDados() throws Exception {
        if (!idUsuario.isBlank() && !idUsuario.isEmpty())
            deletarUsuario();
        if (!idRule.isBlank() && !idRule.isEmpty())
            deletarRule();
    }

    private void deletarUsuario() throws Exception {
        var copyUser = idUsuario;
        idUsuario = "";
        getMockMvc().perform(getDelete(API_URL + SIGNUP_URL + "/" + copyUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        idUsuario = "";
    }

    private void deletarRule() throws Exception {
        var copyRule = idRule;
        idRule = "";
        getMockMvc().perform(getDelete(API_URL + AUTHORITY_URL + "/" + copyRule)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        idRule = "";
    }

    public Map<String, String> salvarUsuario() throws Exception {
        Map<String, String> saveds = new HashMap<>();
        var authoritieSaved = saveAuthoritie("ROLE_ADMIN");
        var userSaved = saveUser(Set.of(AuthorityRequest.builder().name("ROLE_ADMIN").build()));
        saveds.put(userSaved.getId(), authoritieSaved.getId());
        return saveds;
    }

    @Test
    @Rollback
    public void successfulAuthenticationWithUser() throws Exception {
        getMockMvc().perform(post(API_URL + AUTHENTICATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\": \"&senh4Mui$togrande!@\", \"username\": \"douglasferreira\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id_token")));
    }

    @Test
    @Rollback
    public void successfulAuthenticationWithAdmin() throws Exception {
        getMockMvc().perform(post(API_URL + AUTHENTICATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\": \"&senh4Mui$togrande!@\", \"username\": \"douglasferreira\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id_token")));
    }

    @Test
    @Rollback
    public void realizarLoginComSucesso() throws Exception {
//      saveAuthoritie("ROLE_ADMIN");
//      saveUser(Set.of(AuthorityRequest.builder().name("ROLE_ADMIN").build()));
        ResultActions result = getMockMvc().perform(LogInUtils.realizarLoginComoAdmin("douglasferreira", "&senh4Mui$togrande!@"))
                .andDo(log())
                .andExpect(status().isOk());

        JWTTokenResponse token = getMapper().readValue(result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), JWTTokenResponse.class);

        Assert.assertNotNull(token.getIdToken());
    }

    private UserRequest getUsuarioAdmin() {
        var admin = AuthorityRequest.builder().name("RULE_ADMIN").build();
        var user = AuthorityRequest.builder().name("RULE_USER").build();
        return UserRequest.builder()
                .authorities(Set.of(admin, user))
                .email("douglas.versato@gmail.com")
                .lastname("Eleutério")
                .firstname("Douglas")
                .username("douglas")
                .password("Senha#@1Forte")
                .build();
    }

    @Test()
    @Rollback
    public void realizarLoginComNomeUsuarioOuSenhaIncorreto() throws Exception {
//      saveAuthoritie("ROLE_ADMIN");
//      saveUser(Set.of(AuthorityRequest.builder().name("ROLE_ADMIN").build()));
        getMockMvc().perform(LogInUtils.realizarLoginComoAdmin("amin", "admin"))
                .andDo(log())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Rollback
    public void unsuccessfulAuthenticationWithDisabled() throws Exception {
//      saveAuthoritie("ROLE_ADMIN");
//      saveUser(Set.of(AuthorityRequest.builder().name("ROLE_ADMIN").build()));
        getMockMvc().perform(post(API_URL + AUTHENTICATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\": \"password\", \"username\": \"disabled\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(not(containsString("id_token"))));
    }

    @Test
    @Rollback
    public void unsuccessfulAuthenticationWithWrongPassword() throws Exception {
        getMockMvc().perform(post(API_URL + AUTHENTICATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\": \"wrong\", \"username\": \"user\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(not(containsString("id_token"))));
    }

    @Test
    @Rollback
    public void unsuccessfulAuthenticationWithNotExistingUser() throws Exception {
        getMockMvc().perform(post(API_URL + AUTHENTICATE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\": \"password\", \"username\": \"not_existing\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(not(containsString("id_token"))));
    }

    @Test
    @Rollback
    public void successfulRefreshToken() throws Exception {
//      saveAuthoritie("ROLE_ADMIN");
//      saveUser(Set.of(AuthorityRequest.builder().name("ROLE_ADMIN").build()));
        final String token = getTokenForLogin("douglasferreira", "&senh4Mui$togrande!@", getMockMvc());

        getMockMvc().perform(get(API_URL + "token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id_token")));

    }

    @Test
    @Rollback
    public void unsuccessfulRefreshToken() throws Exception {
        getMockMvc().perform(get(API_URL + "token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(not(containsString("id_token"))));
    }
}
