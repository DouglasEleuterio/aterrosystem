package br.com.douglas.controler.security.rest;

import br.com.douglas.controler.util.BaseTest;
import br.com.douglas.entity.entities.Authority;
import br.com.douglas.entity.entities.User;
import br.com.douglas.exception.exceptions.DomainException;
import br.com.douglas.message.messages.Message;
import br.com.douglas.repository.repositories.TokenRepository;
import br.com.douglas.repository.repositories.UserRepository;
import br.com.douglas.service.impls.user.TokenService;
import br.com.douglas.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class ForgotPasswordTest extends BaseTest {

    private static final String REDIRECT_URL = "http://google.com";
    private final String FORGOT_PASSWORD = "recover";

    @Autowired
    ObjectMapper mapper;

    @SpyBean
    UserRepository userRepository;
    @SpyBean
    TokenRepository tokenRepository;

    @SpyBean
    UserService userService;
    @SpyBean
    TokenService tokenService;

    @Before
    public void prepareData() throws DomainException {
        var auth = Authority.builder()
                .id(UUID.randomUUID().toString())
                .name("ROLE_ADMIN")
                .build();
        var authSet = new HashSet<Authority>();
        authSet.add(auth);
        userService.create(User.builder()
                .username("douglasferreira")
                .password("!$#3SenhaForte$S")
                .firstname("Douglas")
                .lastname("Eleutério")
                .email("douglas.versato@gmail.com")
                .activated(true)
                .authorities(authSet)
                .build());
    }

    @Test
    public void forgotPasswordWithUserNameCorrect() throws Exception {
        ResultActions resultActions = getMockMvc().perform(getPost(API_URL + FORGOT_PASSWORD + "/douglasferreira?url-redirect="+REDIRECT_URL)
                .contentType(MediaType.APPLICATION_JSON));
        expectContent(resultActions);
    }

    private void expectContent(ResultActions actions) throws Exception {
        actions.andExpect(result -> {
            getStatus().isAccepted();
            Assert.assertTrue(Objects.requireNonNull(result.getResponse().getRedirectedUrl()).contains(REDIRECT_URL));
        }).andDo(MockMvcResultHandlers.log());
    }

    @Test
    public void forgotPasswordWithUserNameInCorrect() throws Exception {
        getMockMvc().perform(getPost(API_URL + FORGOT_PASSWORD + "/ ?url-redirec=http://google.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(getStatus().isBadRequest())
                .andDo(MockMvcResultHandlers.log())
                .andExpect(result -> {
                    result.getResponse().getContentAsString(StandardCharsets.UTF_8).contains(Message.toLocale("user.username-not-found"));
                });
    }
}
