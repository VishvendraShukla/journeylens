package com.vishvendra.journeylens.analytics.processor;

import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingRequestData;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingResponseData;
import com.vishvendra.journeylens.analytics.requestresponse.Metadata;

public interface AnalyticProcessor {

  default void registerCommandByCommandAndCategoryName(String commandCategoryName,
      String commandName) {
    AnalyticsProcessorRegistry.registerCommand(commandName, this);
    AnalyticsProcessorRegistry.registerCommandWithCategory(commandCategoryName, this);
  }

  Metadata getMetadata();

  AnalyticsProcessingResponseData execute(AnalyticsProcessingRequestData inputs);
}
