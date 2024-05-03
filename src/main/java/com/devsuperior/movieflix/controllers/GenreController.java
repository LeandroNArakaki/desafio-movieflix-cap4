package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.services.GenreService;
import com.devsuperior.movieflix.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/genres")
public class GenreController {

	@Autowired
	private GenreService service;
	
    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping()
	public ResponseEntity<List<GenreDTO>> getAllGenres() {
		return ResponseEntity.ok(service.findAll());
	}
}
