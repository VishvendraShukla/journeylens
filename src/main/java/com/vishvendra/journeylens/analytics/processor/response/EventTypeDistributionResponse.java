package com.vishvendra.journeylens.analytics.processor.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventTypeDistributionResponse {

  private String eventType;
  private Long eventCount;
}