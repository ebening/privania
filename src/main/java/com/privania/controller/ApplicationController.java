package com.privania.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.privania.business.vo.RestResponseVO;

@Controller
public class ApplicationController {

	@RequestMapping(value="/", method = RequestMethod.GET)
	public @ResponseBody RestResponseVO home (HttpServletRequest request, HttpServletResponse response) {
		RestResponseVO r = new RestResponseVO();
		r.setSuccess(Boolean.FALSE);
		return r;
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public @ResponseBody RestResponseVO logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		RestResponseVO r = new RestResponseVO();
		r.setSuccess(Boolean.TRUE);
		return r;
	}

	@RequestMapping(value="/main", method = RequestMethod.GET)
	public String AuthSuccess (HttpServletRequest request, HttpServletResponse response) {
		RestResponseVO r = new RestResponseVO();
		r.setSuccess(Boolean.TRUE);
		Map<String,String> data = new HashMap<String, String>();
		for(Cookie cookie: request.getCookies()) {
			if(cookie.getName().equals("JSESSIONID"))
				data.put("token", cookie.getValue());
		}
		r.setData(data);
		return "main";
	}

	@RequestMapping(value = "/AuthFailure", method = RequestMethod.GET)
	public @ResponseBody RestResponseVO accessDeniedPage(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("failure");
		RestResponseVO r = new RestResponseVO();
		r.setSuccess(Boolean.FALSE);
		r.setErrorCode(10000l);
		r.setErrorDescription("Bad credentials");
		return r;
	}
	
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public @ResponseBody RestResponseVO loginAsked(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("denied");
		RestResponseVO r = new RestResponseVO();
		r.setSuccess(Boolean.FALSE);
		r.setErrorCode(10001l);
		r.setErrorDescription("Access Denied");
		return r;
	}

	@SuppressWarnings("unused")
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
