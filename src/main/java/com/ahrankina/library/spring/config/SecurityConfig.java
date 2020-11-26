package com.ahrankina.library.spring.config;

import com.ahrankina.library.spring.auth.AuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthHandler authHandler;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from library.user where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, role from library.user_roles where username = ?").passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/pages/dictionaries.xhtml").hasRole("ADMIN")
                .antMatchers("/pages/books.xhtml").hasAnyRole("ADMIN", "USER")

                .and()

                .exceptionHandling().accessDeniedPage("/index.xhtml")
                .and()

                .csrf().disable()

                .formLogin()
                .loginPage("/index.xhtml")
                .failureHandler(authHandler)
                .defaultSuccessUrl("/pages/books.xhtml")
                .loginProcessingUrl("/login")
                .passwordParameter("password")
                .usernameParameter("username")
                .and()

                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index.xhtml")
                .deleteCookies("JSESSIONID", "SPRING_SECURITY_REMEMBER_ME_COOKIE")
                .invalidateHttpSession(true);
    }
}
