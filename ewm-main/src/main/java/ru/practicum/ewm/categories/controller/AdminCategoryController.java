package ru.practicum.ewm.categories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.categories.service.CategoryService;
import ru.practicum.ewm.categories.dto.CategoryDto;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @PatchMapping("/{catId}")
    public CategoryDto update(@Positive @PathVariable Long catId,
                              @Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.update(catId, categoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable Long catId) {
        categoryService.delete(catId);
    }
}