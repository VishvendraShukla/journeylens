package com.vishvendra.journeylens.controller;

import com.vishvendra.journeylens.service.event.EventService;
import com.vishvendra.journeylens.utils.model.EventRequestDto;
import com.vishvendra.journeylens.utils.response.ApiResponseSerializer;
import com.vishvendra.journeylens.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventsRestController {

  private final EventService eventService;

  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response> saveEvent(@RequestBody EventRequestDto eventRequestDto) {
    this.eventService.createEvent(eventRequestDto);
    return ApiResponseSerializer.successResponseSerializerBuilder()
        .withStatusCode(HttpStatus.CREATED)
        .withMessage(eventRequestDto.getEventType() + " Event created successfully")
        .build();
  }

}
