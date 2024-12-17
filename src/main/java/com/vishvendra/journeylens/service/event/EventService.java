package com.vishvendra.journeylens.service.event;

import com.vishvendra.journeylens.entities.Event;
import com.vishvendra.journeylens.utils.model.EventRequestDto;

public interface EventService {

  Event createEvent(EventRequestDto eventRequestDto);

}
