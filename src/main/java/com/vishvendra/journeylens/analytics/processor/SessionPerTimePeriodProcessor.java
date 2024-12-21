package com.vishvendra.journeylens.analytics.processor;

import com.vishvendra.journeylens.analytics.processor.response.SessionsPerTimePeriodResponse;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingRequestData;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingResponseData;
import com.vishvendra.journeylens.analytics.requestresponse.InputRequirement;
import com.vishvendra.journeylens.analytics.requestresponse.InputRequirementVOBuilder;
import com.vishvendra.journeylens.analytics.requestresponse.Metadata;
import com.vishvendra.journeylens.exception.event.EventRequestMalformedException;
import com.vishvendra.journeylens.repository.AnalyticsRepository;
import com.vishvendra.journeylens.utils.DateTimeConversionUtil;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionPerTimePeriodProcessor implements AnalyticProcessor {

  @Autowired
  private AnalyticsRepository analyticsRepository;

  public SessionPerTimePeriodProcessor() {
    registerCommandByCommandAndCategoryName("SESSION", "SESSIONS_PER_TIME_PERIOD");
  }

  @Override
  public Metadata getMetadata() {
    return new Metadata("SESSIONS_PER_TIME_PERIOD", true, List.of(
        InputRequirementVOBuilder.builder()
            .dateDataType()
            .key("startDate")
            .displayValue("Start Date")
            .build(),
        InputRequirementVOBuilder.builder()
            .dateDataType()
            .key("endDate")
            .displayValue("End Date")
            .build()));
  }

  @Override
  public AnalyticsProcessingResponseData execute(AnalyticsProcessingRequestData inputs) {
    String startDate = inputs.getInputRequirements().stream()
        .filter(inputRequirement -> "startDate".equalsIgnoreCase(inputRequirement.getKey()))
        .map(InputRequirement::getValue)
        .findFirst()
        .orElse(null);

    String endDate = inputs.getInputRequirements().stream()
        .filter(inputRequirement -> "endDate".equalsIgnoreCase(inputRequirement.getKey()))
        .map(InputRequirement::getValue)
        .findFirst()
        .orElse(null);
    validateDates(startDate, endDate);
    Optional<List<SessionsPerTimePeriodResponse>> sessionsPerTimePeriod = analyticsRepository.findSessionsPerTimePeriod(
        DateTimeConversionUtil.convertToStartOfDay(startDate),
        DateTimeConversionUtil.convertToEndOfDay(endDate));

    if (sessionsPerTimePeriod.isEmpty()) {
      return AnalyticsProcessingResponseData.builder()
          .data(Map.of("sessionsPerTimePeriod", "No Data found!")).build();
    }
    return AnalyticsProcessingResponseData.builder()
        .data(Map.of("sessionsPerTimePeriod", sessionsPerTimePeriod.get())).build();
  }

  private void validateDates(String startDate, String endDate) {
    if (Objects.isNull(startDate) || Objects.isNull(endDate)) {
      throw new EventRequestMalformedException();
    }
    if (startDate.isEmpty() || endDate.isEmpty()) {
      throw new EventRequestMalformedException();
    }
  }
}
