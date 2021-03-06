package simple.mvc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
        .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
        .authoritiesByUsernameQuery("SELECT user_id, role AS authority FROM roles WHERE user_id = (SELECT id FROM users WHERE username = ?)");
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
      .authorizeRequests()
          .antMatchers("/ui-resources/**", "/").permitAll()
          .antMatchers(HttpMethod.POST, "/loginuser").permitAll()
          .anyRequest().authenticated()
          .and()
      .formLogin()
          .loginPage("/login")
          .loginProcessingUrl("/loginuser")
          .usernameParameter("username").passwordParameter("password")
          .permitAll()
          .and()
      .logout()
          .logoutUrl("/logout")
          .deleteCookies("JSESSIONID")
          .permitAll()
          .and()
       .csrf().disable();
    }
    
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }
}
