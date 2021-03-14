package com.privania.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.addFilter(getCustomAuthenticationFilter("/login", "/main", "/AuthFailure"))
		.authorizeRequests().accessDecisionManager(accessDecisionManager())
	  	.antMatchers("/denied").permitAll()
	  	.antMatchers("/AuthFailure").permitAll()
		.antMatchers("/admin/**").access("hasRole('ADMIN')")
		.antMatchers("/**").access("hasAnyRole('USER','ADMIN')")
		.and().formLogin()
			.loginPage("/denied")
			.usernameParameter("username")
			.passwordParameter("password")
		.and().exceptionHandling().accessDeniedPage("/denied")
		;
	}
	
	@Bean
	public CustomAuthenticationFilter getCustomAuthenticationFilter(String loginProcessingUrl, String successUrl, String failureUrl){
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter(successUrl, failureUrl);
	    filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(loginProcessingUrl));
		filter.setAuthenticationManager(authenticationManagerBean());
		return filter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() {
		try {
			return super.authenticationManagerBean();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Bean
	public AccessDecisionManager accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<AccessDecisionVoter<? extends Object>>();
		voters.add(new CustomDecisionVoter());
		voters.add(new WebExpressionVoter());
		voters.add(new RoleVoter());
		AffirmativeBased accessDecisionManager= new AffirmativeBased(voters);
		return accessDecisionManager;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("p").password("p").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
	}
	/*
	//private static RedisServer redisServer;

    @Bean
    public JedisConnectionFactory connectionFactory() throws IOException {
        //redisServer = new RedisServer(Protocol.DEFAULT_PORT);
        //redisServer.start();
    	System.out.println("PGG - Connection Factory done...");
        return new JedisConnectionFactory();
    }

    @PreDestroy
    public void destroy() {
        redisServer.stop();
    }
    
    
    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
    	System.out.println("PGG - HeaderHttpSessionStrategy done...");
        return new HeaderHttpSessionStrategy();
    }
*/
}
