package ru.practicum.ewm.compilation.controller;

import ru.practicum.ewm.compilation.service.CompilationsService;
import ru.practicum.ewm.compilation.dto.ResponseCompilationDto;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
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

