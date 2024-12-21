package com.vishvendra.journeylens.repository;

import com.vishvendra.journeylens.entities.Event;
import com.vishvendra.journeylens.entities.EventType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

  @Query(value = "SELECT AVG(event_count) FROM (SELECT COUNT(*) as event_count FROM event GROUP BY user_id) subQuery", nativeQuery = true)
  Optional<Double> findAverageEventsPerUserNative();

  Page<Event> findByEventTypeAndUserId(EventType eventType, String userId, Pageable pageable);

  Page<Event> findByUserId(String userId, Pageable pageable);

}
