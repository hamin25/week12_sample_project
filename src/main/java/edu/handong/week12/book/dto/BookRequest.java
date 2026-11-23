package edu.handong.week12.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BookRequest (
        @NotBlank(message = "필수입력")String title, @NotBlank(message="필수입력")String author, @Min(value=0, message="0원 이상 필수")int price
){}
