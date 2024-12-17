package com.vishvendra.journeylens.repository;

import com.vishvendra.journeylens.entities.ApiKey;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

  Optional<ApiKey> findByApiKey(String apiKey);
}
