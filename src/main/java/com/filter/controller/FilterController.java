package com.filter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filter.annotation.ActivateMobileFilterPolicy;

@RestController
@RequestMapping
public class FilterController {

	@ActivateMobileFilterPolicy
	@GetMapping("/with-filter")
	public ResponseEntity<String> requestWithFilter() {
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/without-filter")
	public ResponseEntity<String> requestWithoutFilter() {
		return ResponseEntity.ok().build();
	}
	
}
