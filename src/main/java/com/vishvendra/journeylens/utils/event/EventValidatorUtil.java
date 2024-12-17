package com.vishvendra.journeylens.utils.event;

import com.vishvendra.journeylens.exception.event.EventRequestMalformedException;
import com.vishvendra.journeylens.utils.model.EventRequestDto;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class EventValidatorUtil {

  public void validateRequest(EventRequestDto eventRequestDto)
      throws EventRequestMalformedException {

    if (Objects.isNull(eventRequestDto)) {
      throw new EventRequestMalformedException();
    }
    if (Objects.isNull(eventRequestDto.getName()) || eventRequestDto.getName().isEmpty()) {
      throw new EventRequestMalformedException();
    }
    if (Objects.isNull(eventRequestDto.getUserId()) || eventRequestDto.getUserId().isEmpty()) {
      throw new EventRequestMalformedException();
    }

    if (Objects.isNull(eventRequestDto.getEventType()) || eventRequestDto.getEventType()
        .isEmpty()) {
      throw new EventRequestMalformedException();
    }

  }
}
