package com.example.secureswagger.config;

//import com.example.secureswagger.security.JwtConfigurer;
//import com.example.secureswagger.security.JwtProvider;
import com.example.secureswagger.security.JwtConfigurer;
import com.example.secureswagger.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtProvider jwtTokenProvider;

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/index.html",
            "/login",
            "/webjars/**"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui/index.html",
                "/login",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ... here goes your custom security configuration
//        http.authorizeRequests()
//                .antMatchers(AUTH_WHITELIST).permitAll()
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .csrf().disable();
        http.authorizeRequests().
                antMatchers(AUTH_WHITELIST).permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/person/**")
                .authenticated();
        http.apply(new JwtConfigurer(jwtTokenProvider));
//                antMatchers("/**").authenticated(); // others need auth
    }
}