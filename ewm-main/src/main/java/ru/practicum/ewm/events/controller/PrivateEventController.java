package ru.practicum.ewm.events.controller;

import ru.practicum.ewm.events.dto.EventRequestStatusUpdateRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.ewm.events.service.PrivateEventService;
import org.springframework.web.bind.annotation.GetMapping;
import ru.practicum.ewm.requests.service.RequestsService;
import ru.practicum.ewm.events.dto.EventUpdateRequestDto;
import ru.practicum.ewm.requests.dto.RequestUpdateDto;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.PageRequest;
import ru.practicum.ewm.events.dto.CreateEventDto;
import ru.practicum.ewm.events.dto.ShortEventDto;
import ru.practicum.ewm.events.dto.FullEventDto;
import ru.practicum.ewm.requests.dto.RequestDto;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {

    private final PrivateEventService privateEventService;

    private final RequestsService requestsService;

    @GetMapping
    public List<ShortEventDto> getEventsByCreator(@Positive @PathVariable Long userId,
                                                  @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageable = PageRequest.of(from / size, size);
        return privateEventService.getEventsByCreator(userId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FullEventDto create(@Positive @PathVariable Long userId,
                               @Valid @RequestBody CreateEventDto createEventDto) {
        return privateEventService.create(userId, createEventDto);
    }

    @GetMapping("/{eventId}")
    public FullEventDto getEventInfoByCreator(@Positive @PathVariable Long userId,
                                              @Positive @PathVariable Long eventId) {
        return privateEventService.getEventInfoByCreator(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public FullEventDto updateEvent(@Positive @PathVariable Long userId,
                                    @Positive @PathVariable Long eventId,
                                    @RequestBody EventUpdateRequestDto eventUpdateRequestDto) {
        return privateEventService.updateEventByCreator(userId, eventId, eventUpdateRequestDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> findEventRequests(@Positive @PathVariable Long userId,
                                              @Positive @PathVariable Long eventId) {
        return requestsService.findByEventIdAndInitiatorId(eventId, userId);
    }

    @PatchMapping("/{eventId}/requests")
    public RequestUpdateDto requestProcessing(@Positive @PathVariable Long userId,
                                              @Positive @PathVariable Long eventId,
                                              @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return requestsService.requestProcessing(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
