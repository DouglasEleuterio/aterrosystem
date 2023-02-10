package br.com.douglas.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public class CorsConfig {

   private final ApplicationProperties properties;

   public CorsConfig(ApplicationProperties properties) {
      this.properties = properties;
   }

   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
//      config.setMaxAge(300L);
      config.setAllowCredentials(true);
      config.addAllowedOrigin("*"); // e.g. http://domain1.com
      config.addAllowedHeader("*");
      config.addAllowedMethod("*");

      source.registerCorsConfiguration("/api/**", config);
      return new CorsFilter(source);
   }

   private CorsConfiguration origensPermitidas(CorsConfiguration config) {
      properties.getCorsOrigensPermitidas().forEach(value -> {
         if(value.isEmpty() || value.isBlank())
            config.addAllowedOrigin("*");
         else
            config.addAllowedOrigin(value);
      });
      return config;
   }

}
