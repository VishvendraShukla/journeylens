package com.vishvendra.journeylens.analytics.requestresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@Builder
public class AnalyticsProcessingResponseData {

  private Map<String, Object> data;
}
