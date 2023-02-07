package br.com.douglas.controler.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractRestControllerTest {

   protected final String API_URL = "/api/";

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper mapper;

   public MockMvc getMockMvc() {
      return mockMvc;
   }

   public ObjectMapper getMapper() {
      return mapper;
   }

   protected MockHttpServletRequestBuilder getPost(@NotNull String url){
      return post(url).characterEncoding(StandardCharsets.UTF_8);
   }

   protected MockHttpServletRequestBuilder getDelete(@NotNull String url){
      return delete(url).characterEncoding(StandardCharsets.UTF_8);
   }

   protected MockHttpServletRequestBuilder getPut(@NotNull String url){
      return put(url).characterEncoding(StandardCharsets.UTF_8);
   }

   protected MockHttpServletRequestBuilder getGet(@NotNull String url){
      return get(url).characterEncoding(StandardCharsets.UTF_8);
   }

   protected StatusResultMatchers getStatus(){
      return  status();
   }

   protected ResultHandler getLog(){
      return  log();
   }

   protected ContentResultMatchers getContent(){
      return content();
   }

   protected org.mockito.Mockito getMockito(){
      return new org.mockito.Mockito();
   }

}
