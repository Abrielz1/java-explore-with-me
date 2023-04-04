package ru.practicum.ewm.events.dto;

import ru.practicum.ewm.requests.dto.RequestDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateResult {

    List<RequestDto> confirmedRequests;

    List<RequestDto> rejectedRequests;
}
