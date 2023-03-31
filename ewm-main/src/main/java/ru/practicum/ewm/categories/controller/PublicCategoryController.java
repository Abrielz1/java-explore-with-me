package ru.practicum.ewm.categories.controller;

import ru.practicum.ewm.categories.service.CategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.data.domain.PageRequest;
import ru.practicum.ewm.categories.dto.CategoryDto;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findAll(
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {
        PageRequest pageable = PageRequest.of(from / size, size);
        return categoryService.findAll(pageable);
    }

    @GetMapping("/{catId}")
    public CategoryDto findById(@Positive @PathVariable Long catId) {
        return categoryService.findById(catId);
    }
}
