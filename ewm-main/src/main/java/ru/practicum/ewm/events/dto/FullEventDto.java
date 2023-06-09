package ru.practicum.ewm.events.dto;

import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.user.dto.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FullEventDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    private Integer confirmedRequests;

    private String createdOn;

    private String description;

    private String eventDate;

    private UserShortDto initiator;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private String publishedOn;

    private Boolean requestModeration;

    private String state;

    private String title;

    private Long views;

    private Long rating;
}
