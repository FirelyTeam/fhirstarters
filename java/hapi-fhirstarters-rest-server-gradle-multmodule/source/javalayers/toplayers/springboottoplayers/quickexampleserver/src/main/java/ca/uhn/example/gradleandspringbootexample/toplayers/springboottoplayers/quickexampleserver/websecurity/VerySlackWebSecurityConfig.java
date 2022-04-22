package ca.uhn.example.gradleandspringbootexample.toplayers.springboottoplayers.quickexampleserver.websecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class VerySlackWebSecurityConfig extends WebSecurityConfigurerAdapter {

   /**
    * @param http
    * @throws Exception
    */
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests().anyRequest().permitAll();
   }

}
