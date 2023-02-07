package br.com.douglas.controler.util;


import br.com.douglas.controller.mapper.mappers.authoritie.AuthorityRequest;
import br.com.douglas.controller.mapper.mappers.user.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BaseTest extends AbstractRestControllerTest {

   private static final String AUTHORITY_URL = "authority";
   private final String SIGNIN_URL = "signup";


   protected MockHttpServletRequestBuilder getPost(@NotNull String url){
      return post(url).characterEncoding(StandardCharsets.UTF_8);
   }

   protected MockHttpServletRequestBuilder getGet(@NotNull String url){
      return get(url).characterEncoding(StandardCharsets.UTF_8);
   }

   protected StatusResultMatchers getStatus(){
      return  status();
   }

   protected ContentResultMatchers getContent(){
      return content();
   }

   protected org.mockito.Mockito getMockito(){
      return new org.mockito.Mockito();
   }

   @Test
   public void test(){

   }

   public void signUpWithSuccess(Set<AuthorityRequest> auths) throws Exception {
      getMockMvc().perform(post(API_URL + SIGNIN_URL)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(getMapper().writeValueAsString(createUserWithAuthorities(auths, "&senh4Mui$togrande!@"))))
              .andExpect(getStatus().isOk())
              .andDo(MockMvcResultHandlers.log())
              .andExpect(getContent().string(containsString("douglas")));
   }

   protected void createAuthorities(String authoritieName) throws Exception {
      var admin = AuthorityRequest.builder().name(authoritieName).build();

      getMockMvc().perform(post(API_URL + AUTHORITY_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(getMapper().writeValueAsString(admin)))
              .andExpect(getStatus().isOk());
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
}
