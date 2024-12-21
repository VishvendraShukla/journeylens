package com.vishvendra.journeylens.analytics.requestresponse;

public class InputRequirementVOBuilder {

  private String key;
  private String displayValue;
  private String dataType;
  private String description;
  private String[] options;

  private InputRequirementVOBuilder() {

  }

  public static InputRequirementVOBuilder builder() {
    return new InputRequirementVOBuilder();
  }


  public InputRequirementVOBuilder key(String key) {
    this.key = key;
    return this;
  }

  public InputRequirementVOBuilder displayValue(String displayValue) {
    this.displayValue = displayValue;
    return this;
  }

  public InputRequirementVOBuilder eventList() {
    this.dataType = "Select";
    this.options = new String[]{"ALL", "CLICK", "VIEW", "PURCHASE", "LOGIN", "LOGOUT", "ERROR"};
    return this;
  }

  public InputRequirementVOBuilder dateDataType() {
    this.dataType = "Date";
    return this;
  }

  public InputRequirementVOBuilder textDataType() {
    this.dataType = "Text";
    return this;
  }

  public InputRequirementVOBuilder numberDataType() {
    this.dataType = "Number";
    return this;
  }

  public InputRequirementVOBuilder description(String description) {
    this.description = description;
    return this;
  }

  public InputRequirement build() {
    return InputRequirement.builder()
        .key(this.key)
        .displayValue(this.displayValue)
        .dataType(this.dataType)
        .options(this.options)
        .description(this.description)
        .build();
  }
}
