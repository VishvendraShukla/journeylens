package com.vishvendra.journeylens.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "session",
    indexes = {
        @Index(name = "idx_session_user_id", columnList = "user_id"),
        @Index(name = "idx_session_created_at", columnList = "created_at"),
    }
)
@Getter
@Setter
@Builder
public class Session extends AbstractEntity {

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "start_time", nullable = false)
  private LocalDateTime startTime;

  @Column(name = "end_time")
  private LocalDateTime endTime;

  @OneToMany(mappedBy = "session", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  private Set<Event> events;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "metadata", columnDefinition = "jsonb")
  private Map<String, String> metadata;
}

