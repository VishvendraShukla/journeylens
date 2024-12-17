package com.vishvendra.journeylens.controller;

import com.vishvendra.journeylens.analytics.processor.AnalyticProcessor;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingRequestData;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingResponseData;
import com.vishvendra.journeylens.analytics.processor.AnalyticsProcessorRegistry;
import com.vishvendra.journeylens.analytics.requestresponse.Metadata;
import com.vishvendra.journeylens.utils.response.ApiResponseSerializer;
import com.vishvendra.journeylens.utils.response.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsRestController {

  @GetMapping
  public ResponseEntity<Response> getMetadata(@RequestParam final String categoryName) {
    List<Metadata> metadata = AnalyticsProcessorRegistry
        .getCommandListByCategory(categoryName)
        .stream()
        .map(AnalyticProcessor::getMetadata)
        .toList();
    return ApiResponseSerializer.successResponseSerializerBuilder().withData(metadata).build();
  }

  @PostMapping
  public ResponseEntity<Response> getAnalytics(
      @RequestBody final AnalyticsProcessingRequestData requestData) {
    AnalyticsProcessingResponseData responseData = AnalyticsProcessorRegistry
        .getCommandByCommandName(requestData.getCommandName())
        .execute(requestData);
    return ApiResponseSerializer.successResponseSerializerBuilder().withData(responseData).build();
  }

}
