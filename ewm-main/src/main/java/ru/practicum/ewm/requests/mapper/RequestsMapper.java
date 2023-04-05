package ru.practicum.ewm.requests.mapper;

import ru.practicum.ewm.requests.model.ParticipationRequest;
import ru.practicum.ewm.requests.dto.RequestDto;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

@Mapper
public interface RequestsMapper {

    RequestsMapper REQUESTS_MAPPER = Mappers.getMapper(RequestsMapper.class);

    @Mapping(target = "event", source = "event.id")
    @Mapping(target = "requester", source = "requester.id")
    RequestDto toRequestDto(ParticipationRequest participationRequest);
}
