package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

	@Autowired
	private MovieService service;
	
    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping("/{id}")
	public ResponseEntity<MovieDetailsDTO> findMovieById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping
	public ResponseEntity<Page<MovieCardDTO>> findAllMoviePaged(
			@RequestParam(value = "genreId", defaultValue = "0") String genreId,
			Pageable pageable) {
		return ResponseEntity.ok(service.findAllMoviePaged(genreId, pageable));
	}


}
