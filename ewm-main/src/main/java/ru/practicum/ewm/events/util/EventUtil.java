package ru.practicum.ewm.events.util;

import ru.practicum.ewm.requests.repository.RequestsRepository;
import ru.practicum.ewm.requests.model.ParticipationRequest;
import ru.practicum.ewm.rating.repository.RatingRepository;
import ru.practicum.ewm.events.dto.EventUpdateRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import ru.practicum.ewm.events.dto.UserActionState;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.practicum.ewm.events.dto.ShortEventDto;
import ru.practicum.ewm.events.dto.FullEventDto;
import java.time.format.DateTimeParseException;
import ru.practicum.ewm.rating.dto.RatingView;
import ru.practicum.ewm.events.dto.EventState;
import ru.practicum.ewm.statistic.StatService;
import ru.practicum.ewm.events.model.Event;
import java.time.format.DateTimeFormatter;
import ru.practicum.ewm.dto.ViewStatsDto;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.List;
import java.util.Map;

public class EventUtil {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final LocalDateTime MAX_TIME = toTime("5000-01-01 00:00:00");
    public static final LocalDateTime MIN_TIME = toTime("2000-01-01 00:00:00");

    public static List<FullEventDto> getViews(List<FullEventDto> eventDtos, StatService statService) {
        Map<String, FullEventDto> views = eventDtos.stream()
                .collect(Collectors.toMap(fullEventDto -> "/events/" + fullEventDto.getId(),
                        fullEventDto -> fullEventDto));
        Object responseBody = statService.getViewStats(toString(MIN_TIME),
                        toString(MAX_TIME),
                        new ArrayList<>(views.keySet()),
                        false)
                .getBody();
        List<ViewStatsDto> viewStatsDtos = new ObjectMapper().convertValue(responseBody, new TypeReference<>() {
        });
        viewStatsDtos.forEach(viewStatsDto -> {
            if (views.containsKey(viewStatsDto.getUri())) {
                views.get(viewStatsDto.getUri()).setViews(viewStatsDto.getHits());
            }
        });
        return new ArrayList<>(views.values());
    }

    public static List<ShortEventDto> getViewsToShort(List<ShortEventDto> eventDtos, StatService statService) {
        Map<String, ShortEventDto> views = eventDtos.stream()
                .collect(Collectors.toMap(fullEventDto -> "/events/" + fullEventDto.getId(),
                        fullEventDto -> fullEventDto));
        Object responseBody = statService.getViewStats(toString(MIN_TIME),
                        toString(MAX_TIME),
                        new ArrayList<>(views.keySet()),
                        false)
                .getBody();
        List<ViewStatsDto> viewStatsDtos = new ObjectMapper().convertValue(responseBody, new TypeReference<>() {
        });
        viewStatsDtos.forEach(viewStatsDto -> {
            if (views.containsKey(viewStatsDto.getUri())) {
                views.get(viewStatsDto.getUri()).setViews(viewStatsDto.getHits());
            }
        });
        return new ArrayList<>(views.values());
    }

    public static void getConfirmedRequests(List<FullEventDto> eventDtos,
                                            RequestsRepository requestsRepository) {
        List<Long> ids = eventDtos.stream()
                .map(FullEventDto::getId)
                .collect(Collectors.toList());
        List<ParticipationRequest> requests = requestsRepository.findConfirmedToListEvents(ids);
        Map<Long, Integer> counter = new HashMap<>();
        requests.forEach(element -> counter.put(element.getEvent().getId(),
                counter.getOrDefault(element.getEvent().getId(), 0) + 1));
        eventDtos.forEach(event -> event.setConfirmedRequests(counter.get(event.getId())));
    }

    public static void getConfirmedRequestsToShort(List<ShortEventDto> eventDtos,
                                                   RequestsRepository requestsRepository) {
        List<Long> ids = eventDtos.stream()
                .map(ShortEventDto::getId)
                .collect(Collectors.toList());
        List<ParticipationRequest> requests = requestsRepository.findConfirmedToListEvents(ids);
        Map<Long, Integer> counter = new HashMap<>();
        requests.forEach(element -> counter.put(element.getEvent().getId(),
                counter.getOrDefault(element.getEvent().getId(), 0) + 1));
        eventDtos.forEach(event -> event.setConfirmedRequests(counter.get(event.getId())));
    }

    public static void getRatingToFullEvents(List<FullEventDto> eventDtos, RatingRepository ratingRepository) {
        List<Long> ids = eventDtos.stream()
                .map(FullEventDto::getId)
                .collect(Collectors.toList());
        List<RatingView> likes = ratingRepository.getLikes(ids);
        List<RatingView> dislikes = ratingRepository.getDislikes(ids);
        Map<Long, Long> counter = new HashMap<>();
        likes.forEach(element -> counter.put(element.getEventId(), element.getTotal()));
        dislikes.forEach(element -> {
            if (counter.containsKey(element.getEventId())) {
                counter.put(element.getEventId(), counter.get(element.getEventId()) - element.getTotal());
            } else {
                counter.put(element.getEventId(), -element.getTotal());
            }
        });
        eventDtos.forEach(element -> element.setRating(counter.getOrDefault(element.getId(), 0L)));
    }

    public static void getRatingToShortEvents(List<ShortEventDto> eventDtos, RatingRepository ratingRepository) {
        List<Long> ids = eventDtos.stream()
                .map(ShortEventDto::getId)
                .collect(Collectors.toList());
        List<RatingView> likes = ratingRepository.getLikes(ids);
        List<RatingView> dislikes = ratingRepository.getDislikes(ids);
        Map<Long, Long> counter = new HashMap<>();
        likes.forEach(element -> counter.put(element.getEventId(), element.getTotal()));
        dislikes.forEach(element -> {
            if (counter.containsKey(element.getEventId())) {
                counter.put(element.getEventId(), counter.get(element.getEventId()) - element.getTotal());
            } else {
                counter.put(element.getEventId(), -element.getTotal());
            }
        });
        eventDtos.forEach(element -> element.setRating(counter.getOrDefault(element.getId(), 0L)));
    }

    public static void toEventFromUpdateRequestDto(Event event,
                                                   EventUpdateRequestDto eventUpdateRequestDto) {
        if (Objects.equals(eventUpdateRequestDto.getStateAction(), UserActionState.CANCEL_REVIEW.name())) {
            event.setEventState(EventState.CANCELED);
        }
        if (Objects.equals(eventUpdateRequestDto.getStateAction(), UserActionState.SEND_TO_REVIEW.name())) {
            event.setEventState(EventState.PENDING);
        }
        if (eventUpdateRequestDto.getAnnotation() != null) {
            event.setAnnotation(eventUpdateRequestDto.getAnnotation());
        }
        if (eventUpdateRequestDto.getDescription() != null) {
            event.setDescription(eventUpdateRequestDto.getDescription());
        }
        if (eventUpdateRequestDto.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(eventUpdateRequestDto.getEventDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (eventUpdateRequestDto.getPaid() != null) {
            event.setPaid(eventUpdateRequestDto.getPaid());
        }
        if (eventUpdateRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventUpdateRequestDto.getParticipantLimit());
        }
        if (eventUpdateRequestDto.getRequestModeration() != null) {
            event.setRequestModeration(eventUpdateRequestDto.getRequestModeration());
        }
        if (eventUpdateRequestDto.getTitle() != null) {
            event.setTitle(eventUpdateRequestDto.getTitle());
        }
    }

    public static String toString(LocalDateTime localDateTime) {
        return localDateTime.format(FORMATTER);
    }

    public static LocalDateTime toTime(String timeString) throws DateTimeParseException {
        return LocalDateTime.parse(timeString, FORMATTER);
    }
}
