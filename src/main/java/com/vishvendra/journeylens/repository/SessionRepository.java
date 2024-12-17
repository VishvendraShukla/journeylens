package com.vishvendra.journeylens.repository;

import com.vishvendra.journeylens.entities.Session;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

  @Query("SELECT s FROM Session s WHERE s.userId = :userId AND s.endTime > :currentTime")
  Optional<Session> findActiveSession(@Param("userId") String userId,
      @Param("currentTime") LocalDateTime currentTime);
}
