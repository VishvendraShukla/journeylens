package com.vishvendra.journeylens.analytics.processor;

import com.vishvendra.journeylens.analytics.processor.response.TotalEventsByUser;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingRequestData;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingResponseData;
import com.vishvendra.journeylens.analytics.requestresponse.InputRequirement;
import com.vishvendra.journeylens.analytics.requestresponse.InputRequirementVOBuilder;
import com.vishvendra.journeylens.analytics.requestresponse.Metadata;
import com.vishvendra.journeylens.exception.event.EventRequestMalformedException;
import com.vishvendra.journeylens.repository.AnalyticsRepository;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotalEventsPerUserProcessor implements AnalyticProcessor {

  @Autowired
  private AnalyticsRepository analyticsRepository;

  public TotalEventsPerUserProcessor() {
    registerCommandByCommandAndCategoryName("USER_ENGAGEMENT", "TOTAL_EVENTS_PER_USER");
  }

  @Override
  public Metadata getMetadata() {
    return new Metadata("TOTAL_EVENTS_PER_USER", true, List.of(
        InputRequirementVOBuilder.builder()
            .key("userId")
            .textDataType()
            .displayValue("User ID")
            .build()
    ));
  }

  @Override
  public AnalyticsProcessingResponseData execute(AnalyticsProcessingRequestData inputs) {
    InputRequirement userId;
    userId = inputs.getInputRequirements().stream().filter(inputRequirement -> {
      return inputRequirement.getKey().equalsIgnoreCase("userId");
    }).findFirst().orElse(null);

    if (Objects.isNull(userId)) {
      throw new EventRequestMalformedException();
    }
    Optional<List<TotalEventsByUser>> results = analyticsRepository.findEventCountsByUserId(
        userId.getValue());
    if (Objects.isNull(results) || results.isEmpty()) {
      return AnalyticsProcessingResponseData.builder()
          .data(Map.of("totalEventsByUser", "No events found for user")).build();
    }
    return AnalyticsProcessingResponseData.builder()
        .data(Map.of("totalEventsByUser", results.get())).build();
  }

}
