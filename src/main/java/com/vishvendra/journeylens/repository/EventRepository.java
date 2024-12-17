package com.vishvendra.journeylens.repository;

import com.vishvendra.journeylens.entities.Event;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

  @Query(value = "SELECT AVG(event_count) FROM (SELECT COUNT(*) as event_count FROM event GROUP BY user_id) subQuery", nativeQuery = true)
  Optional<Double> findAverageEventsPerUserNative();

}
