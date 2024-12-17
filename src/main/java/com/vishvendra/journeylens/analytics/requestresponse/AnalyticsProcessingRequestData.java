package com.vishvendra.journeylens.analytics.requestresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AnalyticsProcessingRequestData {

  private String commandName;
  private Boolean requiresInput;
  private List<InputRequirement> inputRequirements;
  private Parameter parameters;

}
