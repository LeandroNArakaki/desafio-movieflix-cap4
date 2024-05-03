package com.devsuperior.movieflix.services;


import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public ReviewDTO insert(ReviewDTO dto) {
        Review review = new Review();
        copyDtotoEntity(dto, review);
        review = repository.save(review);
        return new ReviewDTO(review);
    }

    private void copyDtotoEntity(ReviewDTO dto, Review review) {
        review.setText(dto.getText());
        review.setMovie(movieRepository.getReferenceById(dto.getMovieId()));

        UserDTO userDTO = userService.getProfile();
        review.setUser(userRepository.getReferenceById(userDTO.getId()));
    }
}
