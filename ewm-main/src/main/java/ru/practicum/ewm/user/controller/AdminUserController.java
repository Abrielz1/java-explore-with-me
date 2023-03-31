package ru.practicum.ewm.user.controller;

import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.user.service.AdminUserService;
import javax.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import ru.practicum.ewm.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public List<UserDto> findAll(@RequestParam(required = false) List<Long> ids,
                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                 @Positive @RequestParam(defaultValue = "10") Integer size) {
        PageRequest page = PageRequest.of(from / size, size);
        return adminUserService.findAll(ids, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return adminUserService.create(userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long userId) {
        adminUserService.delete(userId);
    }
}