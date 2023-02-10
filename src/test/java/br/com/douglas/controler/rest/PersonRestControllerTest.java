package br.com.douglas.controler.rest;

import br.com.douglas.controler.util.BaseTest;
import br.com.douglas.controler.util.LogInUtils;
import br.com.douglas.mapper.mappers.authoritie.AuthorityRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonRestControllerTest extends BaseTest {

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
   public void getPersonForUser() throws Exception {
      final String token = LogInUtils.getTokenForLogin("douglasferreira", "&senh4Mui$togrande!@", getMockMvc());

      assertSuccessfulPersonRequest(token);
   }

   @Test
   public void getPersonForAdmin() throws Exception {
      final String token = LogInUtils.getTokenForLogin("douglasferreira", "&senh4Mui$togrande!@", getMockMvc());

      assertSuccessfulPersonRequest(token);
   }

   @Test
   public void getPersonForAnonymous() throws Exception {
      getMockMvc().perform(get("/api/person")
         .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isUnauthorized());
   }

   private void assertSuccessfulPersonRequest(String token) throws Exception {
      getMockMvc().perform(get("/api/person")
         .contentType(MediaType.APPLICATION_JSON)
         .header("Authorization", "Bearer " + token))
         .andExpect(status().isOk());
//         .andExpect(content().json(
//            "{\n" +
//               "  \"name\" : \"John Doe\",\n" +
//               "  \"email\" : \"john.doe@test.org\"\n" +
//               "}"
//         ));
   }
}
