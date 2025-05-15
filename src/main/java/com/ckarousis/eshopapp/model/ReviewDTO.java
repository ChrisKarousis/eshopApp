package com.ckarousis.eshopapp.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private Long productId;
    private int rating;
    private String comment;
}
