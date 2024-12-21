package com.vishvendra.journeylens.analytics.processor;

import com.vishvendra.journeylens.analytics.processor.response.MostCommonEventTypesResponse;
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
public class CommonEventTypesProcessor implements AnalyticProcessor {

  @Autowired
  private AnalyticsRepository analyticsRepository;

  public CommonEventTypesProcessor() {
    registerCommandByCommandAndCategoryName("EVENT_TYPE", "COMMON_EVENT_TYPES");
  }

  @Override
  public Metadata getMetadata() {
    return new Metadata("COMMON_EVENT_TYPES", false, null);
  }

  @Override
  public AnalyticsProcessingResponseData execute(AnalyticsProcessingRequestData inputs) {
    Optional<List<MostCommonEventTypesResponse>> commonEventTypes = this.analyticsRepository.findMostCommonEventTypes();
    if (commonEventTypes.isEmpty()) {
      return AnalyticsProcessingResponseData.builder()
          .data(Map.of("commonEventTypes", "No Data found!")).build();
    }
    return AnalyticsProcessingResponseData.builder()
        .data(Map.of("commonEventTypes", commonEventTypes.get())).build();
  }
}
