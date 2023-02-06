package br.com.douglas.controler.security;

import br.com.douglas.shared.TokenUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EncodingTest {

    @SpyBean
    TokenUtils tokenUtils;

    @Autowired
    ApplicationContext context;

    @Test
    public void encodingPassword(){
        tokenUtils.getPasswordEncoder().encode("123456");
    }
}
