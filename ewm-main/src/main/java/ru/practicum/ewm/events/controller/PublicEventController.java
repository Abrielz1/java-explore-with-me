package ru.practicum.ewm.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.events.service.PublicEventService;
import org.springframework.data.domain.PageRequest;
import javax.validation.constraints.PositiveOrZero;
import ru.practicum.ewm.events.dto.ShortEventDto;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.events.dto.FullEventDto;
import javax.validation.constraints.Positive;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/events")
public class PublicEventController {

    private final PublicEventService publicEventService;

    @GetMapping
    public List<ShortEventDto> findEvents(
          @RequestParam(required = false) String text,
          @RequestParam(required = false) List<Long> categories,
          @RequestParam(required = false) Boolean paid,
          @RequestParam(required = false) String rangeStart,
          @RequestParam(required = false) String rangeEnd,
          @RequestParam(defaultValue = "false") Boolean onlyAvailable,
          @RequestParam(required = false) String sort,
          @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
          @Positive @RequestParam(defaultValue = "10") Integer size,
          HttpServletRequest request) {
        PageRequest pageable = PageRequest.of(from / size, size);
        return publicEventService.findEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, pageable, request);
    }

    @GetMapping("/{id}")
    public FullEventDto findById(
            @PathVariable Long id,
            HttpServletRequest request) {
        return publicEventService.findById(id, request);
    }
}
