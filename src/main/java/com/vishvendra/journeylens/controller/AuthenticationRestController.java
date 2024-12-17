package com.vishvendra.journeylens.controller;

import com.vishvendra.journeylens.entities.AppUser;
import com.vishvendra.journeylens.exception.appuser.AppUserNotFoundException;
import com.vishvendra.journeylens.repository.AppUserRepository;
import com.vishvendra.journeylens.utils.response.ApiResponseSerializer;
import com.vishvendra.journeylens.utils.response.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authenticate")
public class AuthenticationRestController {

  private final DaoAuthenticationProvider customAuthenticationProvider;
  private final AppUserRepository appUserRepository;

  @PostMapping(produces = "application/json", consumes = "application/json")
  public ResponseEntity<Response> authenticate(
      @Valid @RequestBody AuthenticateRequest authenticateRequest) {
    log.info("Authenticating user: {}", authenticateRequest.username);
    final Authentication authentication = new UsernamePasswordAuthenticationToken(
        authenticateRequest.username, authenticateRequest.password);
    final Authentication authenticationCheck;
    try {
      authenticationCheck = this.customAuthenticationProvider.authenticate(
          authentication);
    } catch (AuthenticationException e) {
      log.info("User unauthenticated: {}", authenticateRequest.username);
      throw new BadCredentialsException("Unauthorized access.");
    }
    if (!authenticationCheck.isAuthenticated()) {
      log.info("User unauthenticated: {}", authenticateRequest.username);
      throw new BadCredentialsException("Unauthorized access.");
    }
    log.info("User authenticated: {}", authenticateRequest.username);
    User principal = (User) authenticationCheck.getPrincipal();
    AppUser appUser = this.appUserRepository.findByEmail(principal.getUsername())
        .orElseThrow(() -> new AppUserNotFoundException(
            "User not found with email: " + principal.getUsername()));
    return ApiResponseSerializer.successResponseSerializerBuilder()
        .withStatusCode(HttpStatus.OK)
        .withMessage(
            String.format("Welcome! %s %s.", appUser.getFirstName(), appUser.getLastName()))
        .build();
  }

  public static class AuthenticateRequest {

    @NotNull
    @NotBlank
    public String username;
    @NotNull
    @NotBlank
    public String password;
  }

}
