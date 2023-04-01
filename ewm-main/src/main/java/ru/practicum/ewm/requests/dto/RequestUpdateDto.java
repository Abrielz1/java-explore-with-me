package ru.practicum.ewm.requests.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateDto {

    List<RequestDto> confirmedRequests;

    List<RequestDto> rejectedRequests;
}
