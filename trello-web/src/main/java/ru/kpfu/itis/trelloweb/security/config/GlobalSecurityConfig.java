package ru.kpfu.itis.trelloweb.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.kpfu.itis.trelloweb.security.filter.JwtFilter;

import javax.sql.DataSource;

/**
 * @author Roman Leontev
 * 23:56 21.03.2021
 * group 11-905
 */


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalSecurityConfig {

    @Order(1)
    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        @Qualifier("customUserDetailsService")
        private UserDetailsService userDetailsService;

        @Autowired
        private DataSource dataSource;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/sign_up").permitAll()
                    .antMatchers("/workspace").authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("pass")
                    .defaultSuccessUrl("/workspace")
                    .failureUrl("/login?error")
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                    .rememberMe()
                    .rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository());
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
            jdbcTokenRepository.setDataSource(dataSource);
            return jdbcTokenRepository;
        }
    }

    @Order(2)
    @Configuration
    public static class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter{

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        @Qualifier("customUserDetailsService")
        private UserDetailsService userDetailsService;

        @Autowired
        private JwtFilter jwtFilter;

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http
//                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers("/api/workspace").authenticated()
                    .antMatchers("/api/refresh").permitAll()
                    .antMatchers("/api/login").permitAll()
                    .and()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

    }

}
