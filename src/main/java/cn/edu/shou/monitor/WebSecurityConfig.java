package cn.edu.shou.monitor;

import cn.edu.shou.monitor.service.AuthoritiesRepository;
import cn.edu.shou.monitor.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Created by sqhe on 14-7-12.
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDAO userDao;
    @Autowired
    private AuthoritiesRepository authDao;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
//            http
//                .authorizeRequests()
//                .antMatchers("/", "/home").permitAll();
//                .anyRequest().authenticated();

//        http
//                .csrf().disable();
//
        http

                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .and()
                .httpBasic();



//            http    .authorizeRequests()
//                    .antMatchers("/missive/**")
//                    .hasAnyRole()
//                    .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                    .and()
//                    .logout()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .permitAll();
//
//            http
//                    .authorizeRequests()
//                    .antMatchers("/api/**").hasRole("USER")
//                    .and()
//                    .httpBasic();

    }
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        auth.userDetailsService(userDao);
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enabled from users where username=?")
                .authoritiesByUsernameQuery("select users.username,authorities.authority from authorities,users where authorities.user_id=users.id and users.username=?");


        //.passwordEncoder(bcpe);
//        JdbcUserDetailsManager userManager = auth.jdbcAuthentication().dataSource(dataSource)
//                //.withDefaultSchema()
////                .usersByUsernameQuery(
////                        "select username,password, enabled from user where username=?")
////                .authoritiesByUsernameQuery(
////                        "select username, role from user_roles where username=?")
//                .passwordEncoder(bcpe).getUserDetailsService();
//        if (!userManager.userExists("sqhe18") && !userManager.userExists("kermit"))
//        {
//            auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(bcpe)
//            .withUser("sqhe18").password(bcpe.encode("19831028")).roles("USER","ADMIN")
//                .and().withUser("kermit").password(bcpe.encode("123")).roles("USER");
//        }else
//        {
//            auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(bcpe);
//
//
//        }


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/index")
                .antMatchers("/fonts/**")
                .antMatchers("/assets/**")
                .antMatchers("/css/**")
                .antMatchers("/img/**")
                .antMatchers("/js/**")
                .antMatchers("/styles/**");

    }
}
