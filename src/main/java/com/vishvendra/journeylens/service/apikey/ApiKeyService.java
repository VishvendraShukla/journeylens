package com.vishvendra.journeylens.service.apikey;


import org.springframework.security.core.userdetails.User;

public interface ApiKeyService {

  String generateApiKey(String username);

  Boolean validateApiKey(String apiKey);

  String updateKey(User contextUser);

  void changeState(User contextUser);
}
