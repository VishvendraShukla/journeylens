package com.vishvendra.journeylens.utils.event;

import com.vishvendra.journeylens.entities.Event;
import com.vishvendra.journeylens.entities.EventType;
import com.vishvendra.journeylens.utils.model.EventRequestDto;
import org.springframework.stereotype.Component;

@Component
public class EventMapperUtil {

  public Event.EventBuilder mapAndReturnBuilder(EventRequestDto eventRequestDto) {
    Event.EventBuilder eventBuilder = Event.builder();
    eventBuilder
        .name(eventRequestDto.getName())
        .description(eventRequestDto.getDescription())
        .eventType(EventType.valueOf(eventRequestDto.getEventType()))
        .userId(eventRequestDto.getUserId())
        .pageUrl(eventRequestDto.getPageUrl())
        .deviceInfo(eventRequestDto.getDeviceInfo())
        .referrerUrl(eventRequestDto.getReferrerUrl())
        .metadata(eventRequestDto.getMetadata())
        .duration(eventRequestDto.getDuration())
        .userAgent(eventRequestDto.getUserAgent())
        .ipAddress(eventRequestDto.getIpAddress())
        .geoLocation(eventRequestDto.getGeoLocation());
    return eventBuilder;
  }

}
