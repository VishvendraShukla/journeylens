package com.vishvendra.journeylens.service.user;

import com.vishvendra.journeylens.entities.ApiKey;
import com.vishvendra.journeylens.entities.AppUser;
import com.vishvendra.journeylens.exception.appuser.AppUserNotFoundException;
import com.vishvendra.journeylens.repository.AppUserRepository;
import com.vishvendra.journeylens.utils.hash.HashService;
import com.vishvendra.journeylens.utils.model.AppUserApiKeyDetailsDTO;
import com.vishvendra.journeylens.utils.model.AppUserDTO;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("appUserService")
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

  private final AppUserRepository appUserRepository;
  private final HashService hashService;

  @Override
  public AppUserDTO upsert(AppUserDTO appUser) {
    AppUser.AppUserBuilder userBuilder = AppUser.builder();
    userBuilder.firstName(appUser.getFirstName())
        .lastName(appUser.getLastName())
        .email(appUser.getEmail())
        .uid(UUID.randomUUID().toString())
        .password(this.hashService.encodePassword(appUser.getPassword()));
    return getAppUserDTO(this.appUserRepository.save(userBuilder.build()));
  }

  @Override
  public AppUserDTO get(Long id) throws AppUserNotFoundException {
    AppUser appUser = this.appUserRepository.findById(id)
        .orElseThrow(
            () -> new AppUserNotFoundException(String.format("User not found with id: %s", id)));
    return getAppUserDTO(appUser);
  }

  @Override
  public void createAdmin() {
    AppUser.AppUserBuilder userBuilder = AppUser.builder();
    userBuilder.firstName("system")
        .lastName("admin")
        .email("system.admin@journeylens.com")
        .uid(UUID.randomUUID().toString())
        .password(this.hashService.encodePassword("xJsOHQsfk5"));
    this.appUserRepository.save(userBuilder.build());
  }

  @Override
  public AppUserApiKeyDetailsDTO retrieveByUsername(String username)
      throws AppUserNotFoundException {
    AppUser appUser = this.appUserRepository.findByEmail(username)
        .orElseThrow(() -> new AppUserNotFoundException(
            String.format("User not found using email: %s", username)));
    return getAppUserApiKeyDetailsDTO(appUser);
  }

  private AppUserDTO getAppUserDTO(AppUser appUser) {
    return new AppUserDTO(appUser.getFirstName(), appUser.getLastName(), appUser.getEmail(),
        appUser.getId(), appUser.getUid());
  }


  private AppUserApiKeyDetailsDTO getAppUserApiKeyDetailsDTO(AppUser appUser) {
    ApiKey keyEntity = appUser.getApiKey();
    Boolean hasApiKey = Boolean.FALSE;
    Boolean isActive = null;
    String apiKeyCreatedAt = null;
    String apiKey = null;
    if (Objects.nonNull(keyEntity)) {
      apiKey = keyEntity.getApiKey();
      hasApiKey = Boolean.TRUE;
      isActive = keyEntity.getActive();
      apiKeyCreatedAt = keyEntity.getCreatedAt().toString();
      if (keyEntity.getCreatedAt().isBefore(keyEntity.getUpdatedAt())) {
        apiKeyCreatedAt = keyEntity.getUpdatedAt().toString();
      }
    }
    return new AppUserApiKeyDetailsDTO(appUser.getFirstName(),
        appUser.getLastName(),
        null,
        appUser.getEmail(),
        apiKey, appUser.getId(), appUser.getUid(), hasApiKey, apiKeyCreatedAt,
        isActive);
  }
}
