package br.com.douglas.controler.util;

import br.com.douglas.controller.mapper.mappers.authoritie.AuthorityRequest;
import br.com.douglas.controller.mapper.mappers.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractRestControllerTest {

   protected final String API_URL = "/api/";

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper mapper;

   @Before
   public void setUp() throws Exception {
      SecurityContextHolder.clearContext();
   }

   public MockMvc getMockMvc() {
      return mockMvc;
   }

   public ObjectMapper getMapper() {
      return mapper;
   }


}
