package com.vishvendra.journeylens.analytics.processor.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionsPerTimePeriodResponse {

  private LocalDate date;
  private Long sessionCount;

  public SessionsPerTimePeriodResponse(LocalDate date, Long sessionCount) {
    this.date = date;
    this.sessionCount = sessionCount;
  }
}
