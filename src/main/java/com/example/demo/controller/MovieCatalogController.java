package com.example.demo.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CatalogItem;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalogItem(@PathVariable("userId") String userId) {
		
		return Collections.singletonList(new CatalogItem("Titanic","Test",4));//
	}
	
}
