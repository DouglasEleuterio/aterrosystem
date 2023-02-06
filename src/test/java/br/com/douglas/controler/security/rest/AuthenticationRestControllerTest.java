package br.com.douglas.controler.security.rest;

import br.com.douglas.controller.mapper.mappers.JWTTokenResponse;
import br.com.douglas.controler.util.AbstractRestControllerTest;
import br.com.douglas.controler.util.LogInUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static br.com.douglas.controler.util.LogInUtils.getTokenForLogin;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationRestControllerTest extends AbstractRestControllerTest {

   private final String AUTHENTICATE_URL = "authenticate";


   @Test
   public void successfulAuthenticationWithUser() throws Exception {
      getMockMvc().perform(post(API_URL + AUTHENTICATE_URL)
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"password\", \"username\": \"user\"}"))
         .andExpect(status().isOk())
         .andExpect(content().string(containsString("id_token")));
   }

   @Test
   public void successfulAuthenticationWithAdmin() throws Exception {
      getMockMvc().perform(post(API_URL + AUTHENTICATE_URL)
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"admin\", \"username\": \"admin\"}"))
         .andExpect(status().isOk())
         .andExpect(content().string(containsString("id_token")));
   }

   @Test
   public void realizarLoginComSucesso() throws Exception {
      ResultActions result = getMockMvc().perform(LogInUtils.realizarLoginComoAdmin("admin", "admin"))
         .andDo(log())
         .andExpect(status().isOk());

      JWTTokenResponse token = getMapper().readValue(result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), JWTTokenResponse.class);

      Assert.assertNotNull(token.getIdToken());
   }

   @Test()
   public void realizarLoginComNomeUsuarioOuSenhaIncorreto() throws Exception {
      getMockMvc().perform(LogInUtils.realizarLoginComoAdmin("amin", "admin"))
         .andDo(log())
         .andExpect(status().isUnauthorized());
   }

   @Test
   public void unsuccessfulAuthenticationWithDisabled() throws Exception {
      getMockMvc().perform(post(API_URL + AUTHENTICATE_URL)
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"password\", \"username\": \"disabled\"}"))
         .andExpect(status().isUnauthorized())
         .andExpect(content().string(not(containsString("id_token"))));
   }

   @Test
   public void unsuccessfulAuthenticationWithWrongPassword() throws Exception {
      getMockMvc().perform(post(API_URL  + AUTHENTICATE_URL)
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"wrong\", \"username\": \"user\"}"))
         .andExpect(status().isUnauthorized())
         .andExpect(content().string(not(containsString("id_token"))));
   }

   @Test
   public void unsuccessfulAuthenticationWithNotExistingUser() throws Exception {
      getMockMvc().perform(post(API_URL  + AUTHENTICATE_URL)
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"password\", \"username\": \"not_existing\"}"))
         .andExpect(status().isUnauthorized())
         .andExpect(content().string(not(containsString("id_token"))));
   }

   @Test
   public void successfulRefreshToken() throws Exception {
      final String token = getTokenForLogin("user", "password", getMockMvc());

      getMockMvc().perform(get(API_URL  + "token")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token))
         .andExpect(status().isOk())
         .andExpect(content().string(containsString("id_token")));

   }

   @Test
   public void unsuccessfulRefreshToken() throws Exception{
      getMockMvc().perform(get(API_URL  + "token")
            .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isUnauthorized())
         .andExpect(content().string(not(containsString("id_token"))));
   }

}
