package com.vishvendra.journeylens.analytics.processor.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MostCommonEventTypesResponse {

  private String eventType;
  private Long eventCount;

  public MostCommonEventTypesResponse() {
  }

  public MostCommonEventTypesResponse(String eventType, Long eventCount) {
    this.eventType = eventType;
    this.eventCount = eventCount;
  }
}
