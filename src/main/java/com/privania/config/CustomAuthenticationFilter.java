package com.privania.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.privania.business.entity.User;
import com.privania.business.service.UserService;
import com.privania.utils.Constants;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	@Autowired private UserService userService;
	
	public CustomAuthenticationFilter(String successUrl, String failureUrl){
		super();
		setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler(successUrl));
		setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler(failureUrl));
	}
	
    @Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		Long privadaId = Long.valueOf(request.getParameter("privadaId"));
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		if (username == null) username = "";
		if (password == null) password = "";
		username = username.trim();
		
		Boolean isUserValid = userService.validateUser(Long.valueOf(privadaId), username, password);
		if(isUserValid){
			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			List<String> roles = userService.getUserRoles(privadaId, username);
			if(!roles.isEmpty()){
				for(String role: roles){
					auths.add(new SimpleGrantedAuthority(role));
				}
				UsernamePasswordAuthenticationToken a = new UsernamePasswordAuthenticationToken(username, password, auths);
				setDetails(request, a);
				
				User user = userService.getUser(privadaId, username);
				request.getSession().setAttribute(Constants.SESSION_USER, user);
				
				return a;
			}
		}
		throw new BadCredentialsException("Bad Credentials");
	}
}
