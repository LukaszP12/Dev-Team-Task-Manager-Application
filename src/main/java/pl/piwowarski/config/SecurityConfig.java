package pl.piwowarski.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.dataSource = dataSource;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder)
                .usersByUsernameQuery("select email as principal, password as credentials, true from user where email=?")
                .authoritiesByUsernameQuery("select u.email as principal, r.role as role from user u inner join user_role ur on(u.user_id=ur.user_id) inner join role r on(ur.role_id=r.role_id) where u.email=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**", "/", "/users")
                .permitAll()

                .antMatchers("/tasks/allTasks")
                .hasAnyAuthority("COMMON_USER", "ADMIN_USER")

                .antMatchers("/tasks/create", "/tasks/edit/", "/tasks/set-task-completed/")
                .hasAnyAuthority("ADMIN_USER")

                .antMatchers("/registration/**")
                .hasAnyAuthority("ADMIN_USER")

                .antMatchers("/assign/**")
                .hasAnyAuthority("ADMIN_USER")

                .and()
                .formLogin().loginPage("/login-page").defaultSuccessUrl("/")
                .loginProcessingUrl("/login-page")
                .and()
                .csrf().disable()
                .logout()
                .logoutSuccessUrl("/login-page");

        http.csrf().ignoringAntMatchers("/h2-console/**");
        http.headers().frameOptions().sameOrigin();
    }
}
