package com.vishvendra.journeylens.analytics.processor;

import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingRequestData;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingResponseData;
import com.vishvendra.journeylens.analytics.requestresponse.Metadata;
import com.vishvendra.journeylens.repository.EventRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AverageEventsPerUserProcessor implements AnalyticProcessor {

  @Autowired
  private EventRepository eventRepository;

  public AverageEventsPerUserProcessor() {
    registerCommandByCommandAndCategoryName("USER_ENGAGEMENT", "AVERAGE_EVENTS_PER_USER");
  }

  @Override
  public Metadata getMetadata() {
    return new Metadata("AVERAGE_EVENTS_PER_USER", false, null);
  }

  @Override
  public AnalyticsProcessingResponseData execute(AnalyticsProcessingRequestData inputs) {
    Optional<Double> averageEventsPerUser = eventRepository.findAverageEventsPerUserNative();
    Map<String, Object> response = new HashMap<>();
    if (averageEventsPerUser.isPresent()) {
      response.put("averageEventsPerUser", averageEventsPerUser.get());
      return AnalyticsProcessingResponseData.builder().data(response).build();
    }
    response.put("averageEventsPerUser", 0.00);
    return AnalyticsProcessingResponseData.builder().data(response).build();
  }
}
