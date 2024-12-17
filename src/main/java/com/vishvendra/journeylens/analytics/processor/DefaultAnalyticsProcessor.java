package com.vishvendra.journeylens.analytics.processor;

import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingRequestData;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingResponseData;
import com.vishvendra.journeylens.analytics.requestresponse.Metadata;
import com.vishvendra.journeylens.exception.analytics.ProcessorNotFoundException;

public class DefaultAnalyticsProcessor implements AnalyticProcessor {

  private final String name;
  private final String categoryName;

  public DefaultAnalyticsProcessor(String name, String categoryName) {
    this.name = name;
    this.categoryName = categoryName;
  }

  @Override
  public Metadata getMetadata() {
    throw new ProcessorNotFoundException(
        String.format("Command name: %s or Category nam: %s provided", name, categoryName));
  }

  @Override
  public AnalyticsProcessingResponseData execute(AnalyticsProcessingRequestData inputs) {
    throw new ProcessorNotFoundException(
        String.format("Command name: %s or Category name: %s provided", name, categoryName));
  }
}
