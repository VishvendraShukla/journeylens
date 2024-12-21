package com.vishvendra.journeylens.analytics.requestresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class InputRequirement {

  private String key;
  private String displayValue;
  private String dataType;
  private String description;
  private String value;
  private String[] options;

}
