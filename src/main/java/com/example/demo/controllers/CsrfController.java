package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {

	@GetMapping("/csrf-token")
	public Map<String, String> getCsrfToken(HttpServletRequest request) {
		CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		Map<String, String> response = new HashMap<>();
		response.put("csrfToken", token != null ? token.getToken() : "");
		return response;
	}
}
