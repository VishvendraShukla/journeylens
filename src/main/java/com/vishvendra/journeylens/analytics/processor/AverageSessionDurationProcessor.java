package com.vishvendra.journeylens.analytics.processor;

import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingRequestData;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingResponseData;
import com.vishvendra.journeylens.analytics.requestresponse.Metadata;
import org.springframework.stereotype.Service;

@Service
public class AverageSessionDurationProcessor implements AnalyticProcessor {

  public AverageSessionDurationProcessor() {
    registerCommandByCommandAndCategoryName("SESSION", "AVERAGE_SESSION_DURATION");
  }

  @Override
  public Metadata getMetadata() {
    return new Metadata("AVERAGE_SESSION_DURATION", false, null);
  }

  @Override
  public AnalyticsProcessingResponseData execute(AnalyticsProcessingRequestData inputs) {
    return null;
  }
}
