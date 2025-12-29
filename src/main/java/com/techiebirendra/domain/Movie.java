package com.techiebirendra.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Movie {
    private MovieInfo movieInfo;
    private List<Review> review;

    public Movie(MovieInfo movieInfo, List<Review> review) {
        this.movieInfo = movieInfo;
        this.review = review;
    }
}
