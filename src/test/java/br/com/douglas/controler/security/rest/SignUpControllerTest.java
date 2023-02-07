package br.com.douglas.controler.security.rest;

import br.com.douglas.controller.mapper.mappers.authoritie.AuthorityRequest;
import br.com.douglas.controller.mapper.mappers.user.UserRequest;
import br.com.douglas.controler.util.BaseTest;
import br.com.douglas.message.messages.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Set;

import static org.hamcrest.Matchers.containsString;

public class SignUpControllerTest extends BaseTest {

    private final String SIGNIN_URL = "signup";

    @Autowired
    ObjectMapper mapper;

    @Before
    public void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void signUpWithSuccess() throws Exception {
        createAuthorities("ROLE_ADMIN");
        createAuthorities("ROLE_USER");
        getMockMvc().perform(getPost(API_URL + SIGNIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createUserWithAuthorities(getAuthorityes(), "&senh4Mui$togrande!@"))))
                .andExpect(getStatus().isOk())
                .andDo(MockMvcResultHandlers.log())
                .andExpect(getContent().string(containsString("douglas")));
    }

    @Test
    public void signUpWithInSuccess() throws Exception {
        try {
            getMockMvc().perform(getPost(API_URL + SIGNIN_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(createUserWithAuthorities(getAuthorityes(), "fraca"))))
                    .andExpect(getStatus().isBadRequest());
        }catch (Exception e){
            Assert.assertTrue(e.getMessage().contains(Message.toLocale("senha.invalida")));
        }
    }

    private UserRequest createUserWithAuthorities(Set<AuthorityRequest> auths, String senha) {
        return UserRequest.builder()
                .username("douglasferreira")
                .password(senha)
                .firstname("Douglas")
                .lastname("Eleutério Ferreira")
                .email("douglas-negocios@hotmail.com")
                .authorities(auths)
                .build();
    }

    protected Set<AuthorityRequest> getAuthorityes() {
        return Set.of(
                AuthorityRequest.builder().name("ROLE_ADMIN").build(),
                AuthorityRequest.builder().name("ROLE_USER").build()
        );
    }
}
