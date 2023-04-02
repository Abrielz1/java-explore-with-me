package ru.practicum.ewm.requests.dto;

import static ru.practicum.ewm.pub.PublicConstant.patternConst;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    private Long id;

    @JsonFormat(pattern = patternConst, shape = JsonFormat.Shape.STRING)
    private LocalDateTime created;

    private Long event;

    private Long requester;

    private RequestStatus status;
}
