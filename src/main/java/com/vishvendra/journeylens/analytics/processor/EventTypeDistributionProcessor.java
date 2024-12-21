package com.vishvendra.journeylens.analytics.processor;

import com.vishvendra.journeylens.analytics.processor.response.EventTypeDistributionResponse;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingRequestData;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingResponseData;
import com.vishvendra.journeylens.analytics.requestresponse.Metadata;
import com.vishvendra.journeylens.repository.AnalyticsRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventTypeDistributionProcessor implements AnalyticProcessor {

  @Autowired
  private AnalyticsRepository analyticsRepository;

  public EventTypeDistributionProcessor() {
    registerCommandByCommandAndCategoryName("EVENT_TYPE", "EVENT_TYPE_DISTRIBUTION");
  }

  @Override
  public Metadata getMetadata() {
    return new Metadata("EVENT_TYPE_DISTRIBUTION", false, null);
  }

  @Override
  public AnalyticsProcessingResponseData execute(AnalyticsProcessingRequestData inputs) {
    Optional<List<EventTypeDistributionResponse>> eventTypeDistribution = this.analyticsRepository.findEventTypeDistribution();
    if (eventTypeDistribution.isEmpty()) {
      return AnalyticsProcessingResponseData.builder()
          .data(Map.of("eventTypeDistribution", "No Data found!")).build();
    }
    return AnalyticsProcessingResponseData.builder()
        .data(Map.of("eventTypeDistribution", eventTypeDistribution.get())).build();
  }
}
