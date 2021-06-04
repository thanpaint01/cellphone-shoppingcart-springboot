package nlu.fit.cellphoneapp.config;

import nlu.fit.cellphoneapp.others.BcryptEncoder;
import nlu.fit.cellphoneapp.security.MyAccessDeniedHandler;
import nlu.fit.cellphoneapp.security.MyAuthenticationFailureHandler;
import nlu.fit.cellphoneapp.security.MyAuthenticationSuccessHandler;
import nlu.fit.cellphoneapp.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    private static final String[] PUBLIC_MATCHERS = {
            "/fontawesome/**",
            "admin-component/**",
            "admin-static/**",
            "/cart/**",
            "/login",
            "/product/**",
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/",
            "/home",
            "/index",
            "/about/**",
            "/contact/**",
            "/error/**/*",
            "/console/**",
            "/signup",
            "/add-to-cart",
            "/checkout",
            "/cart/delete",
            "/order",
            "/api/**"

    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers("/user/**").hasAuthority("USER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login").permitAll()
                .loginPage("/").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("remember-me", "JSESSIONID")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler)
                .and()
                .rememberMe().key("uniqueAndSecret").and().csrf().disable().cors();
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailService)
                .passwordEncoder(BcryptEncoder.getInstance());
    }
}
