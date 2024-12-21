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

  public EventRequestDto mapAndReturnDTO(Event event) {
    EventRequestDto eventRequestDto = new EventRequestDto();
    eventRequestDto.setName(event.getName());
    eventRequestDto.setDescription(event.getDescription());
    eventRequestDto.setEventType(event.getEventType().name());
    eventRequestDto.setUserId(event.getUserId());
    eventRequestDto.setPageUrl(event.getPageUrl());
    eventRequestDto.setDeviceInfo(event.getDeviceInfo());
    eventRequestDto.setReferrerUrl(event.getReferrerUrl());
    eventRequestDto.setMetadata(event.getMetadata());
    eventRequestDto.setDuration(event.getDuration());
    eventRequestDto.setUserAgent(event.getUserAgent());
    eventRequestDto.setIpAddress(event.getIpAddress());
    eventRequestDto.setGeoLocation(event.getGeoLocation());
    return eventRequestDto;
  }

}
