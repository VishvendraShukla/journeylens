package com.vishvendra.journeylens.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "j_api_keys")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiKey extends AbstractEntity {

  @Column(name = "api_key", unique = true)
  private String apiKey;

  @Column(name = "active")
  private Boolean active;
}
