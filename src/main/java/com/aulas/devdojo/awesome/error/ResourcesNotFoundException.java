package com.aulas.devdojo.awesome.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourcesNotFoundException extends RuntimeException {
	
	public ResourcesNotFoundException(String message) {
		super(message);
	}
}
