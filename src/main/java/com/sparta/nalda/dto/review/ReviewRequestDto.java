package com.sparta.nalda.dto.review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReviewRequestDto {
    private final String reviewContents;
    private final Integer starSore;

}
