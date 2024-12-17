package com.vishvendra.journeylens.repository;


import com.vishvendra.journeylens.analytics.processor.response.DailyActiveUsersResponse;
import com.vishvendra.journeylens.analytics.processor.response.TotalEventsByUser;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnalyticsRepository {

  Optional<List<DailyActiveUsersResponse>> findDailyActiveUsers(LocalDateTime startDate,
      LocalDateTime endDate);

  Optional<List<TotalEventsByUser>> findEventCountsByUserId(String userId);

}
