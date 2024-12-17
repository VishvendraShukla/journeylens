package com.vishvendra.journeylens.controller;

import com.vishvendra.journeylens.service.user.AppUserService;
import com.vishvendra.journeylens.utils.model.AppUserDTO;
import com.vishvendra.journeylens.utils.response.ApiResponseSerializer;
import com.vishvendra.journeylens.utils.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AppUserRestController {

  private final AppUserService appUserService;

  @GetMapping(value = "{id}", produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response> getAppUser(@PathVariable final Long id) {
    return ApiResponseSerializer.successResponseSerializerBuilder()
        .withData(this.appUserService.get(id)).build();
  }

  @GetMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response> getAppUserByEmail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User appUserDTO = (User) authentication.getPrincipal();
    return ApiResponseSerializer.successResponseSerializerBuilder()
        .withData(this.appUserService.retrieveByUsername(appUserDTO.getUsername())).build();
  }

  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response> createAppUser(
      @Valid @RequestBody final AppUserDTO apiRequestBody) {
    return ApiResponseSerializer.successResponseSerializerBuilder()
        .withStatusCode(HttpStatus.CREATED)
        .withData(this.appUserService.upsert(apiRequestBody)).build();
  }

}
