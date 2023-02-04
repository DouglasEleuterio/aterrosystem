package br.com.douglas.controler.util;


import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BaseTest extends AbstractRestControllerTest{

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
}
