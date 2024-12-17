package com.vishvendra.journeylens.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "j_app_user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser extends AbstractEntity {

  @Column(name = "uid", nullable = false)
  private String uid;
  @Column(name = "first_name", nullable = false)
  private String firstName;
  @Column(name = "last_name", nullable = false)
  private String lastName;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "email", unique = true)
  private String email;
  @OneToOne
  @JoinColumn(name = "api_key_id")
  private ApiKey apiKey;
}
