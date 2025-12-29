package com.techiebirendra.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class MovieReactiveServiceTest {

    private final MovieInfoService movieInfoService = new MovieInfoService();
    private final ReviewService reviewService = new ReviewService();
    private final MovieReactiveService movieReactiveService = new MovieReactiveService(movieInfoService, reviewService);
    @Test
    void getAllMovie() {
        var movieFlux = movieReactiveService.getAllMovie();
        StepVerifier.create(movieFlux)
                .assertNext(movie -> {
                    assertEquals("Batman Begins",movie.getMovieInfo().getName());
                    assertEquals(2,movie.getReview().size());
                })
                .assertNext(movie -> {
                    assertEquals("The Dark Knight",movie.getMovieInfo().getName());
                    assertEquals(2,movie.getReview().size());
                })
                .assertNext(movie -> {
                    assertEquals("Dark Knight Rises",movie.getMovieInfo().getName());
                    assertEquals(2,movie.getReview().size());
                })
                .verifyComplete();
    }

    @Test
    void getMovieById() {
        long movieId = 100l;
        var monoMovie = movieReactiveService.getMovieById(movieId);
        StepVerifier.create(monoMovie)
                .assertNext(movie -> {
                    assertEquals("Batman Begins",movie.getMovieInfo().getName());
                    assertEquals(2,movie.getReview().size());
                })
                .verifyComplete();
    }

    @Test
    void getMovieById2() {
        long movieId = 100l;
        var monoMovie = movieReactiveService.getMovieById2(movieId);
        StepVerifier.create(monoMovie)
                .assertNext(movie -> {
                    assertEquals("Batman Begins",movie.getMovieInfo().getName());
                    assertEquals(2,movie.getReview().size());
                })
                .verifyComplete();
    }

    @Test
    void getMovieByIdFlatMap() {
        long movieId = 100l;
        var monoMovie = movieReactiveService.getMovieByIdFlatMap(movieId);
        StepVerifier.create(monoMovie)
                .assertNext(movie -> {
                    assertEquals("Batman Begins",movie.getMovieInfo().getName());
                    assertEquals(2,movie.getReview().size());
                })
                .verifyComplete();
    }
}