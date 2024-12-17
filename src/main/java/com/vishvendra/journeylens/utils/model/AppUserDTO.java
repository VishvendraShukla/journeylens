package com.vishvendra.journeylens.utils.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserDTO {

  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @NotBlank
  private String password;
  @NotBlank
  @Email
  private String email;
  private Long id;
  private String uid;

  public AppUserDTO(String firstName, String lastName, String email, Long id, String uid) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.id = id;
    this.uid = uid;
  }
}
