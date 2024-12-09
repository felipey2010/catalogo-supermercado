package br.com.sistemadesupermercado.common.domain.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/**
 * This security section checks the roles assigned to a user
 * Access denied page is shown if user tries to visit a page without the due permission
 * @author P. Akpanyi
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AppUserDetailsService userDetailsService() {
        return new AppUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(new Md5PasswordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
        jsfLoginEntry.setLoginFormUrl("/login.xhtml");
        jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());

        JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
        jsfDeniedEntry.setLoginPath("/error/403.xhtml");
        jsfDeniedEntry.setContextRelative(true);

        http
                .csrf().disable()
                .headers().frameOptions().sameOrigin()
                .and()

                .authorizeRequests()
                .antMatchers("/recupera-acesso.xhtml").permitAll()
                .antMatchers("/login.xhtml").permitAll()
                .antMatchers("/javax.faces.resource/**").permitAll()

                .antMatchers("/administracao/cadastro-mensagem-global.xhtml").hasRole("MENSAGEM_GLOBAL")
                .antMatchers("/administracao/cadastro-perfil-acesso.xhtml").hasRole("PERFIS_DE_ACESSO")
                .antMatchers("/administracao/cadastro-usuario.xhtml").hasRole("USUARIO")

                .antMatchers("/pessoal/meu-perfil.xhtml").hasRole("PADRAO")

                .anyRequest().authenticated()
                .and()

                .formLogin()
                .loginPage("/login.xhtml")
                .failureUrl("/login.xhtml?invalid=true")
                .defaultSuccessUrl("/", true)
                .and()

                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout.xhtml"))
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login.xhtml")
                .invalidateHttpSession(true)
                .and()

                .exceptionHandling()
                .accessDeniedPage("/error/403.xhtml")
                .authenticationEntryPoint(jsfLoginEntry)
                .accessDeniedHandler(jsfDeniedEntry)

                .and()
                .sessionManagement()
                .maximumSessions(50)
                .expiredUrl("/login.xhtml?expired");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(authProvider());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**");
    }
}