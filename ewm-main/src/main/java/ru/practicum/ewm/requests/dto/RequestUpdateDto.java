package ru.practicum.ewm.requests.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateDto {

    List<RequestDto> confirmedRequests;

    List<RequestDto> rejectedRequests;
}
