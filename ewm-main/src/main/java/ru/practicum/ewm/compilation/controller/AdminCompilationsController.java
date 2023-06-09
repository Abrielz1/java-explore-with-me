package ru.practicum.ewm.compilation.controller;

import ru.practicum.ewm.compilation.service.CompilationsService;
import ru.practicum.ewm.compilation.dto.ResponseCompilationDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {

    private final CompilationsService compilationsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        return compilationsService.create(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long compId) {
        compilationsService.delete(compId);
    }

    @PatchMapping("/{compId}")
    public ResponseCompilationDto update(@PathVariable Long compId,
                                         @RequestBody NewCompilationDto newCompilationDto) {
        return compilationsService.update(compId, newCompilationDto);
    }
}
