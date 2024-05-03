package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Movie;

import com.devsuperior.movieflix.projections.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM (
                SELECT DISTINCT tb_movie.id, tb_movie.title
            	FROM tb_movie
            	INNER JOIN tb_genre ON tb_genre.id = tb_movie.genre_id
            	WHERE (:genreIds IS NULL OR tb_movie.genre_id IN :genreIds)
            	ORDER BY tb_movie.title
            ) AS tb_result                
            """, countQuery = """
            SELECT COUNT(*) FROM (
                SELECT DISTINCT tb_movie.id, tb_movie.title
            	FROM tb_movie
            	INNER JOIN tb_genre ON tb_genre.id = tb_movie.genre_id
            	WHERE (:genreIds IS NULL OR tb_movie.genre_id IN :genreIds)
            	ORDER BY tb_movie.title
            ) AS tb_result                            
            """)
    Page<MovieProjection> searchMovies(List<Long> genreIds, Pageable pageable);


    @Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj.id IN :movieIds")
    List<Movie> searchMoviesWithGenre(List<Long> movieIds);
}
