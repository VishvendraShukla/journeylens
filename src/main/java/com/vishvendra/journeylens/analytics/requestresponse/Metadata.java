package com.vishvendra.journeylens.analytics.requestresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Metadata {

  private String name;
  private Boolean requiresInput;
  private List<InputRequirement> inputRequirements;

}
