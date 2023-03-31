package ru.practicum.ewm.requests.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
