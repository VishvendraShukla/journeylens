package com.vishvendra.journeylens.utils.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserApiKeyDetailsDTO extends AppUserDTO {

  private Boolean hasApiKey;
  private String apiKey;
  private String apiKeyCreatedAt;
  private Boolean isKeyActive;

  public AppUserApiKeyDetailsDTO(String firstName,
      String lastName,
      String password,
      String email,
      String apiKey, Long id, String uid, Boolean hasApiKey, String apiKeyCreatedAt,
      Boolean isKeyActive) {
    super(firstName, lastName, password, email, id, uid);
    this.hasApiKey = hasApiKey;
    this.apiKey = apiKey;
    this.apiKeyCreatedAt = apiKeyCreatedAt;
    this.isKeyActive = isKeyActive;
  }
}
