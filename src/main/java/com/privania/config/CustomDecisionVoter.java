package com.privania.config;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;

import com.privania.business.entity.User;
import com.privania.utils.Constants;

public class CustomDecisionVoter implements AccessDecisionVoter<FilterInvocation>{

	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

	public int vote(Authentication authentication, FilterInvocation filter, Collection<ConfigAttribute> attrs) {
		String username = getPrincipal();
		if(username.equals(Constants.SESSION_ANONYMOUS_USER)){
			return ACCESS_DENIED;
		}
		
		User user = (User)filter.getHttpRequest().getSession().getAttribute(Constants.SESSION_USER);
		if(user!=null){
//			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//			for(String accessUrl: user.getAccessUrls()){
//				if(resolver.getPathMatcher().match(accessUrl, filter.getRequestUrl()))
//					return ACCESS_GRANTED;
//			}
			
			return ACCESS_GRANTED;
		}
		
		return ACCESS_DENIED;
	}

	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		
		return userName;
	}
}
