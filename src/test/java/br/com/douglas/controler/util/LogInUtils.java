package br.com.douglas.controler.util;

import br.com.douglas.controller.mapper.mappers.LoginDto;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.validation.constraints.NotNull;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public final class LogInUtils {

   private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
   private static final String API = "http://localhost:8080/api";

   private LogInUtils() {
   }

   public static String getTokenForLogin(String username, String password, MockMvc mockMvc) throws Exception {
      String content = mockMvc.perform(post("/api/authenticate")
         .contentType(MediaType.APPLICATION_JSON)
         .content("{\"password\": \"" + password + "\", \"username\": \"" + username + "\"}"))
         .andReturn()
         .getResponse()
         .getContentAsString();
      AuthenticationResponse authResponse = OBJECT_MAPPER.readValue(content, AuthenticationResponse.class);

      return authResponse.getIdToken();
   }

   public static MockHttpServletRequestBuilder realizarLoginComoAdmin(@NotNull final String nomeUsuario, @NotNull final String senha) throws Exception {
      var usuario = LoginDto.builder()
         .password(senha)
         .username(nomeUsuario)
         .rememberMe(true)
         .build();

      return post( API + "/authenticate")
         .contentType(MediaType.APPLICATION_JSON)
         .content(OBJECT_MAPPER.writeValueAsString(usuario));
   }

   private static class AuthenticationResponse {

      @JsonAlias("id_token")
      private String idToken;

      public void setIdToken(String idToken) {
         this.idToken = idToken;
      }

      public String getIdToken() {
         return idToken;
      }
   }
}
