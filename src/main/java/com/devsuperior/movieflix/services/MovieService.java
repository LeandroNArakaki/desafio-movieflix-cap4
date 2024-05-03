package com.devsuperior.movieflix.services;


import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {
        Optional<Movie> movie = repository.findById(id);
        Movie entity = movie.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new MovieDetailsDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findAllMoviePaged(String genreId, Pageable pageable) {
        List<Long> genreIds = Arrays.asList();

        if (!"0".equals(genreId)) {
            genreIds = Arrays.asList(genreId.split(",")).stream().map(Long::parseLong).toList();
        }

        Page<MovieProjection> projections = repository.searchMovies(genreIds, pageable);
        List<Long> movieIds = projections.map(x -> x.getId()).toList();
        List<Movie> entities = repository.searchMoviesWithGenre(movieIds);
        entities = (List<Movie>) Utils.replace(projections.getContent(), entities);

        List<MovieCardDTO> dtos = entities.stream().map(MovieCardDTO::new).toList();
        Page<MovieCardDTO> pageDto = new PageImpl<>(dtos, projections.getPageable(), projections.getTotalElements());

        return pageDto;
    }
}
