package ru.practicum.ewm.rating.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {

    private Long id;

    private Long userId;

    @NotNull
    private Long eventId;

    @NotNull
    @NotBlank
    private String ratingState;
}
