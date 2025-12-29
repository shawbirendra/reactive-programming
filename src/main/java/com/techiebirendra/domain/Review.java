package com.techiebirendra.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long reviewId;
    private Long movieInfoId;
    private String comment;
    private Double rating;
}
