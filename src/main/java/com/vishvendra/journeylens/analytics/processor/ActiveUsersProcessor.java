package com.vishvendra.journeylens.analytics.processor;

import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingRequestData;
import com.vishvendra.journeylens.analytics.requestresponse.AnalyticsProcessingResponseData;
import com.vishvendra.journeylens.analytics.requestresponse.InputRequirement;
import com.vishvendra.journeylens.analytics.requestresponse.InputRequirementVOBuilder;
import com.vishvendra.journeylens.analytics.requestresponse.Metadata;
import com.vishvendra.journeylens.analytics.processor.response.DailyActiveUsersResponse;
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
public class ActiveUsersProcessor implements AnalyticProcessor {

  @Autowired
  private AnalyticsRepository analyticsRepository;

  public ActiveUsersProcessor() {
    registerCommandByCommandAndCategoryName("USER_ENGAGEMENT", "ACTIVE_USERS");
  }

  @Override
  public Metadata getMetadata() {
    return new Metadata("ACTIVE_USERS", true, List.of(
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
    Optional<List<DailyActiveUsersResponse>> dailyActiveUsers = analyticsRepository.findDailyActiveUsers(
        DateTimeConversionUtil.convertToStartOfDay(startDate),
        DateTimeConversionUtil.convertToEndOfDay(endDate));

    if (dailyActiveUsers.isEmpty()) {
      return AnalyticsProcessingResponseData.builder()
          .data(Map.of("activeUsers", "No Data found!")).build();
    }
    return AnalyticsProcessingResponseData.builder()
        .data(Map.of("activeUsers", dailyActiveUsers.get())).build();
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
