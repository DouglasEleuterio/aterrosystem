package br.com.douglas.controler.security;

import br.com.douglas.controler.util.AbstractRestControllerTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChangePasswordCotrollerTest extends AbstractRestControllerTest {

    private static final String RECOVER_URL = "recover";

    @Test
    public void shouldChangePasswordWithToken() throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.post(API_URL + RECOVER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", UUID.randomUUID().toString())
                .param("username", "douglasferreira")
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
    } 
}
