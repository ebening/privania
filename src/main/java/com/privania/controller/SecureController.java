package com.privania.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.privania.business.vo.RestResponseVO;

@Controller
public class SecureController {
	
	@RequestMapping(value="/admin/test")
	public @ResponseBody RestResponseVO getList(HttpServletRequest request, HttpServletResponse response){
		RestResponseVO r = new RestResponseVO();
		r.setSuccess(Boolean.TRUE);
		return r;
	}

}
