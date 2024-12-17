package com.vishvendra.journeylens.service.user;

import com.vishvendra.journeylens.exception.appuser.AppUserNotFoundException;
import com.vishvendra.journeylens.utils.model.AppUserApiKeyDetailsDTO;
import com.vishvendra.journeylens.utils.model.AppUserDTO;

public interface AppUserService {

  AppUserDTO upsert(AppUserDTO appUser);

  AppUserDTO get(Long id) throws AppUserNotFoundException;

  void createAdmin();

  AppUserApiKeyDetailsDTO retrieveByUsername(String username) throws AppUserNotFoundException;

}
