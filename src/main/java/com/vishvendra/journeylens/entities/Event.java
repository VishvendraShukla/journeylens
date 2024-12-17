package com.vishvendra.journeylens.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(
    name = "event",
    indexes = {
        @Index(name = "idx_event_type", columnList = "event_type"),
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_session_id", columnList = "session_id"),
        @Index(name = "idx_created_at", columnList = "created_at"),
        @Index(name = "idx_page_url", columnList = "page_url")
    }
)
@Getter
@Setter
@Builder
public class Event extends AbstractEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "event_type", nullable = false)
  private EventType eventType;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "page_url")
  private String pageUrl;

  @Column(name = "device_info")
  private String deviceInfo;

  @Column(name = "referrer_url")
  private String referrerUrl;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "metadata", columnDefinition = "jsonb")
  private Map<String, String> metadata;

  @Column(name = "duration")
  private Long duration;

  @Column(name = "user_agent")
  private String userAgent;

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "geo_location")
  private String geoLocation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "session_id")
  private Session session;

}
