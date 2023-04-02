package ru.practicum.ewm.compilation.dto;

import ru.practicum.ewm.events.dto.ShortEventDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCompilationDto {

    private Long id;

    private List<ShortEventDto> events;

    private Boolean pinned;

    private String title;
}
