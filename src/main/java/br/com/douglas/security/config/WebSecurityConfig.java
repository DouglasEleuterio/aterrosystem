package br.com.douglas.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import br.com.douglas.security.JwtAccessDeniedHandler;
import br.com.douglas.security.JwtAuthenticationEntryPoint;
import br.com.douglas.security.jwt.JWTConfigurer;
import br.com.douglas.security.jwt.TokenProvider;

import java.util.Arrays;
import java.util.stream.Collectors;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   private final TokenProvider tokenProvider;
   private final CorsFilter corsFilter;
   private final JwtAuthenticationEntryPoint authenticationErrorHandler;
   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   private final UrlsProperties properties;

   public WebSecurityConfig(
      TokenProvider tokenProvider,
      CorsFilter corsFilter,
      JwtAuthenticationEntryPoint authenticationErrorHandler,
      JwtAccessDeniedHandler jwtAccessDeniedHandler,
      UrlsProperties properties) {
      this.tokenProvider = tokenProvider;
      this.corsFilter = corsFilter;
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
      this.properties = properties;
   }

   // Configure BCrypt password encoder =====================================================================

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   // Configure paths and requests that should be ignored by Spring Security ================================

   @Override
   public void configure(WebSecurity web) {
      web.ignoring()
         .antMatchers(HttpMethod.OPTIONS, "/**")
         .antMatchers("/swagger-ui/**", "/bus/v3/api-docs/**")

         // allow anonymous resource requests
         .antMatchers(
            "/",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/h2-console/**"
         );
   }

   // Configure security settings ===========================================================================

   @Override
   protected void configure(HttpSecurity httpSecurity) throws Exception {
      httpSecurity
         // we don't need CSRF because our token is invulnerable
         .csrf().disable()

         .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

         .exceptionHandling()
         .authenticationEntryPoint(authenticationErrorHandler)
         .accessDeniedHandler(jwtAccessDeniedHandler)

         // enable h2-console
         .and()
         .headers()
         .frameOptions()
         .sameOrigin()

         // create no session
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      httpSecurity = adicionarEnderecosPublicos(httpSecurity);
      httpSecurity = adicionarEnderecosPrivadosPeloPerfil(httpSecurity);

      httpSecurity.apply(securityConfigurerAdapter());
   }

   private JWTConfigurer securityConfigurerAdapter() {
      return new JWTConfigurer(tokenProvider);
   }

   private HttpSecurity adicionarEnderecosPublicos(HttpSecurity httpSecurity) {
// .antMatchers("/api/authenticate").permitAll()
// .antMatchers("/api/register").permitAll()
// .antMatchers("/api/activate").permitAll()
// .antMatchers("/api/account/reset-password/init").permitAll()
// .antMatchers("/api/account/reset-password/finish").permitAll()
      properties.getUrlsPublicas().forEach(url -> {
         try {
            httpSecurity.authorizeRequests().antMatchers(url).permitAll();
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
      });
      return httpSecurity;
   }

   private HttpSecurity adicionarEnderecosPrivadosPeloPerfil(HttpSecurity httpSecurity) throws Exception {
//         .antMatchers("/api/person").hasAuthority("ROLE_USER")
//         .antMatchers("/api/hiddenmessage").hasAuthority("ROLE_ADMIN")
      properties.getUrlsPrivadas().forEach((s, s2) ->
         Arrays.stream(s2.split(","))
            .collect(Collectors.toList()).forEach(url ->
            {
               try {
                  httpSecurity.authorizeRequests()
                     .antMatchers(url)
                     .hasAuthority(s.toUpperCase());
               } catch (Exception e) {
                  throw new RuntimeException(e);
               }
            }));
      httpSecurity.authorizeRequests().anyRequest().authenticated();
      return httpSecurity;
   }
}
