package ru.practicum.ewm.rating.mapper;

import ru.practicum.ewm.rating.dto.RatingDto;
import ru.practicum.ewm.rating.model.Rating;
import ru.practicum.ewm.events.model.Event;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

@Mapper
public interface RatingMapper {
    RatingMapper RATING_MAPPER = Mappers.getMapper(RatingMapper.class);

    @Mapping(target = "userId", expression = "java(userId)")
    @Mapping(target = "event", expression = "java(event)")
    @Mapping(target = "id", expression = "java(ratingDto.getId())")
    Rating toRating(RatingDto ratingDto, Long userId, Event event);

    @Mapping(target = "eventId", source = "event.id")
    RatingDto toRatingDto(Rating rating);
}
