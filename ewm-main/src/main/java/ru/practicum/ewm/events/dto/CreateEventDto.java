package ru.practicum.ewm.events.dto;

import static ru.practicum.ewm.pub.PublicConstant.patternConst;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.PositiveOrZero;
import ru.practicum.ewm.location.model.Location;
import javax.validation.constraints.Positive;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventDto {

    @NotNull
    @Size(min = 5, max = 2000)
    private String annotation;

    @NotNull
    @Positive
    private Long category;

    @NotNull
    @Size(max = 5000)
    private String description;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = patternConst)
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    private Boolean paid;

    @PositiveOrZero
    private Integer participantLimit;

    private Boolean requestModeration;

    @NotNull
    @Size(min = 5, max = 500)
    private String title;
}
