package com.privania.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.privania.business.mixin.CargoMixIn;
import com.privania.business.vo.RestResponseVO;


@Component
public class CustomObjectMapper extends ObjectMapper{
	
	private static final long serialVersionUID = 1L;

	public CustomObjectMapper(){
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		setSerializationInclusion(Include.NON_NULL);
		
		Map<Class<?>, Class<?>> mixins = new HashMap<Class<?>, Class<?>>();
		mixins.put(RestResponseVO.class, CargoMixIn.class);
		setMixIns(mixins);
	}
	
}
