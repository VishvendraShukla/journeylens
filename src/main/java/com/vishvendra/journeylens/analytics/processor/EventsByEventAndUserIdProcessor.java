package com.vishvendra.journeylens.analytics.processor;

import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingRequestData;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingResponseData;
import com.vishvendra.journeylens.analytics.requestresponse.InputRequirement;
import com.vishvendra.journeylens.analytics.requestresponse.InputRequirementVOBuilder;
import com.vishvendra.journeylens.analytics.requestresponse.Metadata;
import com.vishvendra.journeylens.entities.Event;
import com.vishvendra.journeylens.entities.EventType;
import com.vishvendra.journeylens.exception.event.EventRequestMalformedException;
import com.vishvendra.journeylens.repository.EventRepository;
import com.vishvendra.journeylens.utils.event.EventMapperUtil;
import com.vishvendra.journeylens.utils.model.EventRequestDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EventsByEventAndUserIdProcessor implements AnalyticProcessor {

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private EventMapperUtil eventMapperUtil;

  public EventsByEventAndUserIdProcessor() {
    registerCommandByCommandAndCategoryName("EVENT_TYPE", "EVENTS_BY_EVENT_AND_USER");
  }

  @Override
  public Metadata getMetadata() {
    return new Metadata("EVENTS_BY_EVENT_AND_USER", true, List.of(
        InputRequirementVOBuilder.builder()
            .textDataType()
            .key("userId")
            .displayValue("User ID")
            .build(),
        InputRequirementVOBuilder.builder()
            .eventList()
            .key("eventType")
            .displayValue("Select Event Type")
            .build()));
  }

  @Override
  public AnalyticsProcessingResponseData execute(AnalyticsProcessingRequestData inputs) {
    InputRequirement userId;
    userId = inputs.getInputRequirements().stream().filter(inputRequirement -> {
      return inputRequirement.getKey().equalsIgnoreCase("userId");
    }).findFirst().orElse(null);

    InputRequirement eventType;
    eventType = inputs.getInputRequirements().stream().filter(inputRequirement -> {
      return inputRequirement.getKey().equalsIgnoreCase("eventType");
    }).findFirst().orElse(null);

    validateInputData(userId);
    validateInputData(eventType);
    assert userId != null;
    assert eventType != null;

    Integer currentPage = inputs.getParameters().getCurrentPage();
    Integer pageSize = inputs.getParameters().getPageSize();
    Pageable pageable = PageRequest.of(currentPage, pageSize);
    if (eventType.getValue().equalsIgnoreCase("ALL")) {

      Page<Event> eventPage = this.eventRepository
          .findByUserId(userId.getValue(), pageable);
      return createResponse(eventPage, currentPage, pageSize);
    }
    Page<Event> eventPage = this.eventRepository
        .findByEventTypeAndUserId(
            EventType.valueOf(eventType.getValue()),
            userId.getValue(),
            pageable);
    return createResponse(eventPage, currentPage, pageSize);
  }

  private AnalyticsProcessingResponseData createResponse(Page<Event> eventPage, Integer currentPage,
      Integer pageSize) {
    Map<String, Object> data = new HashMap<>();
    AnalyticsProcessingResponseData.AnalyticsProcessingResponseDataBuilder builder =
        AnalyticsProcessingResponseData.builder();
    eventPage.getContent();
    if (eventPage.getContent().isEmpty()) {
      data.put("events", "No data Found.");
      data.put("pageSize", pageSize);
      data.put("currentPage", currentPage);
      data.put("total", 0);
      return builder.data(data).build();
    }
    List<EventRequestDto> eventRequestDtoList = eventPage.getContent().stream()
        .map(event -> eventMapperUtil.mapAndReturnDTO(event)).toList();
    data.put("events", eventRequestDtoList);
    data.put("pageSize", pageSize);
    data.put("total", eventPage.getTotalElements());
    data.put("currentPage", currentPage);
    return builder.data(data).build();
  }

  private void validateInputData(InputRequirement requirement) {
    if (Objects.isNull(requirement)) {
      throw new EventRequestMalformedException();
    }
    if (requirement.getValue().isEmpty()) {
      throw new EventRequestMalformedException();
    }
  }
}
