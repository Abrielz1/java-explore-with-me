package ru.practicum.ewm.rating.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import ru.practicum.ewm.rating.service.RatingService;
import javax.validation.constraints.Positive;
import ru.practicum.ewm.rating.dto.RatingDto;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/rating/{userId}")
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto create(@Positive @PathVariable Long userId,
                            @Valid @RequestBody RatingDto ratingDto) {
        return ratingService.create(userId, ratingDto);
    }

    @DeleteMapping("/{ratingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable Long userId,
                       @Positive @PathVariable Long ratingId) {
        ratingService.delete(userId, ratingId);
    }
}
