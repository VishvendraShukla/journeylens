package com.vishvendra.journeylens.service.apikey;

import com.vishvendra.journeylens.entities.ApiKey;
import com.vishvendra.journeylens.entities.AppUser;
import com.vishvendra.journeylens.exception.apikey.ApiKeyNotFoundException;
import com.vishvendra.journeylens.exception.appuser.AppUserNotFoundException;
import com.vishvendra.journeylens.repository.ApiKeyRepository;
import com.vishvendra.journeylens.repository.AppUserRepository;
import com.vishvendra.journeylens.utils.hash.HashService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service("apiKeyService")
@RequiredArgsConstructor
public class ApiKeyServiceImpl implements ApiKeyService {

  private final ApiKeyRepository apiKeyRepository;
  private final AppUserRepository appUserRepository;
  private final HashService hashService;

  @Override
  public String generateApiKey(String username) {
    ApiKey.ApiKeyBuilder apiKeyBuilder = ApiKey.builder();
    String apiKey = this.hashService.generateApiKey();
    apiKeyBuilder.apiKey(apiKey);
    apiKeyBuilder.active(true);
    ApiKey apiKeyEntity = this.apiKeyRepository.save(apiKeyBuilder.build());
    AppUser appUser = this.appUserRepository.findByEmail(username).orElseThrow(
        (() -> new AppUserNotFoundException(
            String.format("User not found with email: %s", username))));
    appUser.setApiKey(apiKeyEntity);
    this.appUserRepository.save(appUser);
    return apiKey;
  }

  @Override
  public Boolean validateApiKey(String apiKey) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User principal = (User) authentication.getPrincipal();
    AppUser appUser = this.appUserRepository.findByEmail(principal.getUsername()).orElseThrow(
        (() -> new AppUserNotFoundException(
            String.format("User not found with email: %s", principal.getUsername()))));
    ApiKey apiKeyEntity = this.apiKeyRepository
        .findByApiKey(apiKey)
        .orElseThrow(
            () -> new ApiKeyNotFoundException(String.format("ApiKey: %s not found", apiKey)));
    if (!apiKeyEntity.getActive()) {
      return Boolean.FALSE;
    }
    if (!appUser.getApiKey().getApiKey().equals(apiKey)) {
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  @Override
  public String updateKey(User contextUser) {
    AppUser appUser = this.appUserRepository.findByEmail(contextUser.getUsername()).orElseThrow(
        (() -> new AppUserNotFoundException(
            String.format("User not found with email: %s", contextUser.getUsername()))));
    ApiKey apiKeyEntity = appUser.getApiKey();
    String newApikey = this.hashService.generateApiKey();
    apiKeyEntity.setApiKey(newApikey);
    apiKeyEntity.setUpdatedAt(LocalDateTime.now());
    this.apiKeyRepository.save(apiKeyEntity);
    return newApikey;
  }

  @Override
  public void changeState(User contextUser) {
    AppUser appUser = this.appUserRepository.findByEmail(contextUser.getUsername()).orElseThrow(
        (() -> new AppUserNotFoundException(
            String.format("User not found with email: %s", contextUser.getUsername()))));
    ApiKey apiKeyEntity = appUser.getApiKey();
    apiKeyEntity.setActive(!apiKeyEntity.getActive());
    this.apiKeyRepository.save(apiKeyEntity);
  }
}
