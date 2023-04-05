package ru.practicum.ewm.events.dto;

import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.user.dto.UserDto;
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
public class ShortEventDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    private Integer confirmedRequests;

    private String eventDate;

    private UserDto.UserShortDto initiator;

    private Boolean paid;

    private String title;

    private Long views;

    private Long rating;
}
