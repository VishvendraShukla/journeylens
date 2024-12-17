package com.vishvendra.journeylens.controller;

import com.vishvendra.journeylens.service.apikey.ApiKeyService;
import com.vishvendra.journeylens.utils.response.ApiResponseSerializer;
import com.vishvendra.journeylens.utils.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/api-keys")
public class ApiKeyRestController {

  private final ApiKeyService apiKeyService;

  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response> createApiKey() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
      User appUserDTO = (User) authentication.getPrincipal();
      return ApiResponseSerializer
          .successResponseSerializerBuilder()
          .withMessage("API Key generated successfully")
          .withStatusCode(HttpStatus.CREATED)
          .withData(this.apiKeyService.generateApiKey(appUserDTO.getUsername()))
          .build();
    }
    return ApiResponseSerializer
        .errorResponseSerializerBuilder()
        .withStatusCode(HttpStatus.BAD_REQUEST)
        .withMessage("Application error occurred, please contact support.")
        .build();
  }

  @PutMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response> updateApiKey() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
      User appUserDTO = (User) authentication.getPrincipal();
      return ApiResponseSerializer
          .successResponseSerializerBuilder()
          .withMessage("API Key updated successfully")
          .withStatusCode(HttpStatus.CREATED)
          .withData(this.apiKeyService.updateKey(appUserDTO))
          .build();
    }
    return ApiResponseSerializer
        .errorResponseSerializerBuilder()
        .withStatusCode(HttpStatus.BAD_REQUEST)
        .withMessage("Application error occurred, please contact support.")
        .build();
  }

  @PutMapping(value = "change-state", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response> changeKeyState() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
      User appUserDTO = (User) authentication.getPrincipal();
      this.apiKeyService.changeState(appUserDTO);
      return ApiResponseSerializer
          .successResponseSerializerBuilder()
          .withMessage("API Key updated successfully")
          .withStatusCode(HttpStatus.CREATED)
          .build();
    }
    return ApiResponseSerializer
        .errorResponseSerializerBuilder()
        .withStatusCode(HttpStatus.BAD_REQUEST)
        .withMessage("Application error occurred, please contact support.")
        .build();
  }

}
