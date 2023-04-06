package ru.practicum.ewm.events.service;

import ru.practicum.ewm.categories.repository.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.location.repository.LocationRepository;
import ru.practicum.ewm.rating.repository.RatingRepository;
import ru.practicum.ewm.requests.repository.RequestsRepository;
import ru.practicum.ewm.user.repository.AdminUserRepository;
import ru.practicum.ewm.exception.ObjectNotFoundException;
import ru.practicum.ewm.events.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.requests.dto.RequestStatus;
import org.springframework.data.domain.PageRequest;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.events.mapper.EventMapper;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.events.util.EventUtil;
import ru.practicum.ewm.statistic.StatService;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.events.model.Event;
import java.time.format.DateTimeFormatter;
import ru.practicum.ewm.user.model.User;
import lombok.RequiredArgsConstructor;
import ru.practicum.ewm.events.dto.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PrivateEventService {

    private final RequestsRepository requestsRepository;

    private final RatingRepository ratingRepository;

    private final LocationRepository locationRepository;

    private final EventRepository eventRepository;

    private final AdminUserRepository adminUserRepository;

    private final CategoryRepository categoryRepository;

    private final StatService statService;

    @Transactional
    public FullEventDto create(Long userId, CreateEventDto createEventDto) {
        if (createEventDto.getEventDate().isBefore(LocalDateTime.now())) {
            throw new ConflictException("Wrong date");
        }
        User initiator = adminUserRepository.findById(userId).orElseThrow(() -> {
            throw new ObjectNotFoundException("User not found");
        });
        Category category = categoryRepository.findById(createEventDto.getCategory()).orElseThrow(() -> {
            throw new ObjectNotFoundException("Category not found");
        });
        createEventDto.setLocation(locationRepository.save(createEventDto.getLocation()));
        Event event = eventRepository.save(EventMapper.EVENT_MAPPER.toEventFromCreateDto(initiator, category, createEventDto));
        FullEventDto fullEventDto = EventMapper.EVENT_MAPPER.toFullEventDto(event);
        fullEventDto.setConfirmedRequests(0);
        fullEventDto.setRating(0L);
        return fullEventDto;
    }

    public List<ShortEventDto> getEventsByCreator(Long userId, PageRequest pageable) {
        List<ShortEventDto> shortEventDtos = eventRepository.findAllByInitiatorId(userId, pageable).stream()
                .map(EventMapper.EVENT_MAPPER::toShortEventDto)
                .collect(Collectors.toList());
        EventUtil.getConfirmedRequestsToShort(shortEventDtos, requestsRepository);
        EventUtil.getRatingToShortEvents(shortEventDtos, ratingRepository);
        return EventUtil.getViewsToShort(shortEventDtos, statService);
    }

    public FullEventDto getEventInfoByCreator(Long userId, Long eventId) {
        Event event = eventRepository.findByInitiatorIdAndId(userId, eventId).orElseThrow();

        FullEventDto fullEventDto = EventMapper.EVENT_MAPPER.toFullEventDto(event);
        fullEventDto.setConfirmedRequests(requestsRepository
                .findAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED).size());
        EventUtil.getRatingToFullEvents(Collections.singletonList(fullEventDto), ratingRepository);
        return EventUtil.getViews(Collections.singletonList(fullEventDto), statService).get(0);
    }

    @Transactional
    public FullEventDto updateEventByCreator(Long userId, Long eventId, EventUpdateRequestDto eventUpdateRequestDto) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ObjectNotFoundException("You can't update this event");
        }
        if (eventUpdateRequestDto.getEventDate() != null) {
            LocalDateTime time = LocalDateTime.parse(eventUpdateRequestDto.getEventDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (LocalDateTime.now().isAfter(time.minusHours(2))) {
                throw new ConflictException("Event starts in less then 2 hours");
            }
        }
        if (event.getEventState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("You can't update published event");
        }
        if (eventUpdateRequestDto.getCategory() != null && !Objects.equals(eventUpdateRequestDto.getCategory(),
                event.getCategory().getId())) {
            Category category = categoryRepository.findById(eventUpdateRequestDto.getCategory()).orElseThrow();
            event.setCategory(category);
        }
        if (eventUpdateRequestDto.getLocation() != null) {
            Location location = locationRepository.save(eventUpdateRequestDto.getLocation());
            event.setLocation(location);
        }
        EventUtil.toEventFromUpdateRequestDto(event, eventUpdateRequestDto);
        FullEventDto fullEventDto = EventMapper.EVENT_MAPPER.toFullEventDto(event);
        fullEventDto.setConfirmedRequests(requestsRepository.findAllByEventIdAndStatus(eventId, RequestStatus.CONFIRMED)
                .size());
        EventUtil.getRatingToFullEvents(Collections.singletonList(fullEventDto), ratingRepository);
        return EventUtil.getViews(Collections.singletonList(fullEventDto), statService).get(0);
    }
}
