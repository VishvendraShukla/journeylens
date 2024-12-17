package com.vishvendra.journeylens.analytics.processor.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyActiveUsersResponse {

  private LocalDate createdAt;
  private Long count;

  public DailyActiveUsersResponse(LocalDate createdAt, Long count) {
    this.createdAt = createdAt;
    this.count = count;
  }
}
