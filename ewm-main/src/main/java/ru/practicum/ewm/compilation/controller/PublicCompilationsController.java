package ru.practicum.ewm.compilation.controller;

import ru.practicum.ewm.compilation.service.CompilationsService;
import ru.practicum.ewm.compilation.dto.ResponseCompilationDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.PageRequest;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationsController {

    private final CompilationsService compilationsService;


    @GetMapping
    public List<ResponseCompilationDto> findAll(
            @RequestParam(required = false) Boolean pinned,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageable = PageRequest.of(from / size, size);
        return compilationsService.findAll(pinned, pageable);
    }

    @GetMapping("/{compId}")
    public ResponseCompilationDto findById(@PathVariable Long compId) {
        return compilationsService.findById(compId);
    }
}
