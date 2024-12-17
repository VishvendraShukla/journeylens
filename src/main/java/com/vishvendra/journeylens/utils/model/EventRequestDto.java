package com.vishvendra.journeylens.utils.model;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRequestDto {

  private String name;
  private String description;
  private String eventType;
  private String userId;
  private String pageUrl;
  private String deviceInfo;
  private String referrerUrl;
  private Map<String, String> metadata;
  private Long duration;
  private String userAgent;
  private String ipAddress;
  private String geoLocation;
}
