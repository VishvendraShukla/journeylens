package com.vishvendra.journeylens.service.user;

import com.vishvendra.journeylens.entities.AppUser;
import com.vishvendra.journeylens.exception.appuser.AppUserNotFoundException;
import com.vishvendra.journeylens.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final AppUserRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = appUserRepository.findByEmail(username)
        .orElseThrow(() -> new AppUserNotFoundException("User not found with email: " + username));

    return User.withUsername(appUser.getEmail())
        .accountExpired(appUser.isDeleted())
        .accountLocked(appUser.isDeleted())
        .password(appUser.getPassword())
        .roles("USER")
        .disabled(appUser.isDeleted())
        .build();
  }
}
