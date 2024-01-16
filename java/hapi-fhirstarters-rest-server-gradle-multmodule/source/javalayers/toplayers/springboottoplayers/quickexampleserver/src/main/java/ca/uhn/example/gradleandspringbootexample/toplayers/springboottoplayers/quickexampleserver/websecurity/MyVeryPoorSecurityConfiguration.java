package ca.uhn.example.gradleandspringbootexample.toplayers.springboottoplayers.quickexampleserver.websecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@SuppressWarnings("checkstyle:DesignForExtension") /* @Configuration classes cannot be final */
public class MyVeryPoorSecurityConfiguration {

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

      /* The below is HORRIBLE "security" and is NOT production level correct */

      http.csrf().disable(); /* had to add this "Cross Site Request Forgery" disable for DELETE operations */
      http.authorizeRequests().anyRequest().permitAll();
      return http.build();
   }

   @Bean
   public WebSecurityCustomizer webSecurityCustomizer() {
      return (web) -> web.ignoring().antMatchers("/images/**", "/js/**");
   }
}
