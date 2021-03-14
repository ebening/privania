package com.privania.business.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CargoMixIn {
	
	@JsonIgnore
    abstract char someAttributeToIgnore();
	
}
