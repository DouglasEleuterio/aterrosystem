package br.com.douglas.controler.util;


import br.com.douglas.controller.mapper.endereco.EnderecoRequest;
import br.com.douglas.controller.mapper.mappers.authoritie.AuthorityRequest;
import br.com.douglas.controller.mapper.mappers.authoritie.AuthorityResponse;
import br.com.douglas.controller.mapper.mappers.user.UserRequest;
import br.com.douglas.controller.mapper.mappers.user.UserResponse;
import br.com.douglas.controller.mapper.transportador.TransportadorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class BaseTest extends AbstractRestControllerTest {

   private static final String AUTHORITY_URL = "authority";
   protected final String SIGNUP_URL = "signup";


   public UserResponse saveUser(Set<AuthorityRequest> auths) throws Exception {
      ResultActions result = getMockMvc().perform(post(API_URL + SIGNUP_URL)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(getMapper().writeValueAsString(createUserWithAuthorities(auths, "&senh4Mui$togrande!@"))))
              .andExpect(getStatus().isOk())
              .andDo(MockMvcResultHandlers.log())
              .andExpect(getContent().string(containsString("douglas")));

      return getMapper().readValue(
              result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
              });
   }

   protected AuthorityResponse saveAuthoritie(String authoritieName) throws Exception {
      var admin = AuthorityRequest.builder().name(authoritieName).build();

      var result = getMockMvc().perform(post(API_URL + AUTHORITY_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(getMapper().writeValueAsString(admin)))
              .andExpect(getStatus().isOk());

      return getMapper().readValue(
              result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<>() {
              });
   }

   private UserRequest createUserWithAuthorities(Set<AuthorityRequest> auths, String senha) {
      return UserRequest.builder()
              .username("douglasferreira")
              .password(senha)
              .firstname("Douglas")
              .lastname("Eleutério Ferreira")
              .email("douglas-negocios@hotmail.com")
              .authorities(auths)
              .activated(true)
              .build();
   }

   protected EnderecoRequest getEnderecoValido() throws Exception {
       return FakerGenerator.builder()
               .param("cep", "76343-000")
               .generate(EnderecoRequest.class, false);
   }
}
