package com.vishvendra.journeylens.repository;

import com.vishvendra.journeylens.analytics.processor.response.DailyActiveUsersResponse;
import com.vishvendra.journeylens.analytics.processor.response.EventTypeDistributionResponse;
import com.vishvendra.journeylens.analytics.processor.response.MostCommonEventTypesResponse;
import com.vishvendra.journeylens.analytics.processor.response.SessionsPerTimePeriodResponse;
import com.vishvendra.journeylens.analytics.processor.response.TotalEventsByUser;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnalyticsRepositoryImpl implements AnalyticsRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public Optional<List<DailyActiveUsersResponse>> findDailyActiveUsers(LocalDateTime startDate,
      LocalDateTime endDate) {

    String sql = """
            SELECT CAST(e.created_at AS date) AS activityDate, COUNT(DISTINCT e.user_id) AS activeUsers
            FROM event e
            WHERE e.created_at BETWEEN ? AND ?
            GROUP BY CAST(e.created_at AS date)
            ORDER BY CAST(e.created_at AS date)
        """;

    try {
      List<DailyActiveUsersResponse> responseList = jdbcTemplate.query(
          sql,
          new Object[]{startDate, endDate},
          (rs, rowNum) -> {
            LocalDate activityDate = rs.getDate("activityDate")
                .toLocalDate();
            Long activeUsers = rs.getLong("activeUsers");
            return new DailyActiveUsersResponse(activityDate, activeUsers);
          }
      );
      return Optional.ofNullable(responseList.isEmpty() ? null : responseList);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<List<TotalEventsByUser>> findEventCountsByUserId(String userId) {
    String sql = """
            SELECT e.event_type, COUNT(*) AS event_count
            FROM event e
            WHERE e.user_id = ?
            GROUP BY e.event_type
        """;

    try {
      List<TotalEventsByUser> responseList = jdbcTemplate.query(
          sql,
          new Object[]{userId},
          (rs, rowNum) -> {
            String eventType = rs.getString("event_type");
            Long eventCount = rs.getLong("event_count");
            return new TotalEventsByUser(eventType, eventCount);
          }
      );
      return Optional.ofNullable(responseList.isEmpty() ? null : responseList);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Long findSessionsPerUser(String userId) {
    String sql = "SELECT COUNT(*) FROM session WHERE user_id = ?";
    return jdbcTemplate.queryForObject(sql, Long.class, userId);
  }

  @Override
  public Optional<List<SessionsPerTimePeriodResponse>> findSessionsPerTimePeriod(
      LocalDateTime startDate, LocalDateTime endDate) {
    String sql = """
            SELECT DATE(s.start_time) AS session_date, COUNT(s.id) AS session_count
            FROM session s
            WHERE s.start_time BETWEEN ? AND ?
            GROUP BY DATE(s.start_time)
            ORDER BY DATE(s.start_time)
        """;

    try {
      List<SessionsPerTimePeriodResponse> responseList = jdbcTemplate.query(
          sql,
          new Object[]{startDate, endDate},
          (rs, rowNum) -> {
            LocalDate sessionDate = rs.getDate("session_date").toLocalDate();
            Long sessionCount = rs.getLong("session_count");
            return new SessionsPerTimePeriodResponse(sessionDate, sessionCount);
          }
      );
      return Optional.ofNullable(responseList.isEmpty() ? null : responseList);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<List<EventTypeDistributionResponse>> findEventTypeDistribution() {
    String sql = """
        SELECT e.event_type, COUNT(*) AS event_count
        FROM event e
        GROUP BY e.event_type
        ORDER BY event_count DESC
    """;
    try {
      List<EventTypeDistributionResponse> responseList = jdbcTemplate.query(
          sql,
          (rs, rowNum) -> {
            String eventType = rs.getString("event_type");
            Long eventCount = rs.getLong("event_count");
            return new EventTypeDistributionResponse(eventType, eventCount);
          }
      );
      return Optional.ofNullable(responseList.isEmpty() ? null : responseList);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public Optional<List<MostCommonEventTypesResponse>> findMostCommonEventTypes() {
    String sql = """
        SELECT event_type, COUNT(*) AS event_count
        FROM event
        GROUP BY event_type
        ORDER BY event_count DESC
        LIMIT 10;
    """;

    try {
      List<MostCommonEventTypesResponse> responseList = jdbcTemplate.query(
          sql,
          (rs, rowNum) -> {
            String eventType = rs.getString("event_type");
            Long eventCount = rs.getLong("event_count");
            return new MostCommonEventTypesResponse(eventType, eventCount);
          }
      );
      return Optional.ofNullable(responseList.isEmpty() ? null : responseList);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }



}
