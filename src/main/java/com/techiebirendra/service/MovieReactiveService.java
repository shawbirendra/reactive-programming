package com.techiebirendra.service;

import com.techiebirendra.domain.Movie;
import com.techiebirendra.domain.Review;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class MovieReactiveService {
    private final MovieInfoService movieInfoService;
    private final ReviewService reviewService;

    public MovieReactiveService(MovieInfoService movieInfoService, ReviewService reviewService) {
        this.movieInfoService = movieInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Movie> getAllMovie(){
        var movieInfoFlux = movieInfoService.retrieveMoviesFlux();
        return movieInfoFlux
                .flatMap(movieInfo -> {
                     Mono<List<Review>> reviewsMono = reviewService.retrieveReviewsFlux(movieInfo.getMovieInfoId())
                    .collectList();
                     return reviewsMono.map(reviewsList -> new Movie(movieInfo, reviewsList));
                })
                .log();

    }


    public Mono<Movie> getMovieById(Long movieId){
        var monoMovieInfo = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
        var movieReview = reviewService.retrieveReviewsFlux(movieId).collectList();
        return monoMovieInfo.zipWith(movieReview,(movieInfo, reviews)->new Movie(movieInfo,reviews))
                .log();
    }

    public Mono<Movie> getMovieById2(Long movieId){
        var monoMovieInfo = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
        return monoMovieInfo.map(movieInfo -> {
            List<Review> reviewList = reviewService.retrieveReviews(movieInfo.getMovieInfoId());
            return new Movie(movieInfo, reviewList);
        }).log();
    }

    public Mono<Movie> getMovieByIdFlatMap(Long movieId){
        var monoMovieInfo = movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
        return monoMovieInfo.flatMap(movieInfo -> {
            Mono<List<Review>> reviewMono = reviewService.retrieveReviewsFlux(movieId)
                    .collectList();
            return reviewMono.map(reviews -> new Movie(movieInfo, reviews))
                    .log();
        });
    }
}
