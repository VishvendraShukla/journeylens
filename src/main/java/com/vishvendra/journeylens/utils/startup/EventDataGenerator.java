package com.vishvendra.journeylens.utils.startup;

import com.vishvendra.journeylens.entities.Event;
import com.vishvendra.journeylens.entities.EventType;
import com.vishvendra.journeylens.entities.Session;
import com.vishvendra.journeylens.repository.EventRepository;
import com.vishvendra.journeylens.repository.SessionRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class EventDataGenerator implements CommandLineRunner {

  private final SessionRepository sessionRepository;
  private final EventRepository eventRepository;
  @Value("${DUMMY_DATA_GENERATOR:false}")
  private Boolean shouldRunDummyDataGenerator;

  public EventDataGenerator(SessionRepository sessionRepository, EventRepository eventRepository) {
    this.sessionRepository = sessionRepository;
    this.eventRepository = eventRepository;
  }

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    if (shouldRunDummyDataGenerator) {
      try {
        generateDummyData(1000, 100);
      } catch (Exception exception) {
        log.error("Error while generating dummy data", exception);
      }
    }
  }

  public void generateDummyData(int sessionCount, int maxEventsPerSession) {
    Random random = new Random();
    List<String> locations = Arrays.asList("New York, USA", "Los Angeles, USA",
        "San Francisco, USA", "Chicago, USA", "Austin, USA");
    List<String> deviceTypes = Arrays.asList("Mobile", "Desktop", "Tablet");
    List<String> pageUrls = Arrays.asList(
        "https://example.com/home",
        "https://example.com/product/12345",
        "https://example.com/product/56789",
        "https://example.com/videos/xyz",
        "https://example.com/checkout"
    );
    List<String> referrerUrls = Arrays.asList(
        "https://google.com",
        "https://example.com/search?q=product",
        "https://example.com/home",
        "https://example.com/videos",
        "https://example.com/cart"
    );
    List<String> userAgents = Arrays.asList(
        "Mozilla/5.0 (Android) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Gecko/20100101 Firefox/94.0",
        "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 Safari/604.1",
        "Mozilla/5.0 (Linux; Android 9.0; Pixel 3 XL) AppleWebKit/537.36 Chrome/80.0.3987.149 Safari/537.36"
    );

    for (int i = 1; i <= sessionCount; i++) {
      Session session = new Session();
      session.setUserId("user_" + i);

      LocalDateTime sessionStart = randomDateBetween("2024-01-01T00:00:00", "2024-11-01T23:59:59");
      session.setStartTime(sessionStart);
      session.setEndTime(sessionStart.plusMinutes(30));
      session.setCreatedAt(sessionStart);

      session.setMetadata(Map.of(
          "device", deviceTypes.get(random.nextInt(deviceTypes.size())),
          "browser", "Browser_" + random.nextInt(3)
      ));
      session = sessionRepository.save(session);

      int eventCount = random.nextInt(maxEventsPerSession) + 1;

      for (int j = 1; j <= eventCount; j++) {
        Event event = new Event();
        event.setName(generateEventName(random));
        event.setDescription("Generated event " + j);
        event.setEventType(EventType.valueOf(event.getName().toUpperCase()));
        event.setUserId(session.getUserId());
        event.setPageUrl(pageUrls.get(random.nextInt(pageUrls.size())));
        event.setReferrerUrl(referrerUrls.get(random.nextInt(referrerUrls.size())));
        event.setDeviceInfo(deviceTypes.get(random.nextInt(deviceTypes.size())));
        event.setDuration((long) random.nextInt(10000));
        event.setUserAgent(userAgents.get(random.nextInt(userAgents.size())));
        event.setIpAddress("192.168." + random.nextInt(255) + "." + random.nextInt(255));
        event.setGeoLocation(locations.get(random.nextInt(locations.size())));
        event.setMetadata(generateRandomMetadata(event.getEventType()));
        LocalDateTime eventCreatedAt = randomDateBetween(
            session.getStartTime().toString(),
            session.getEndTime().toString()
        );
        event.setCreatedAt(eventCreatedAt);
        event.setSession(session);

        eventRepository.save(event);
      }
    }

    log.info("Dummy data generation completed.");
  }


  private LocalDateTime randomDateBetween(String start, String end) {
    LocalDateTime startDateTime = LocalDateTime.parse(start);
    LocalDateTime endDateTime = LocalDateTime.parse(end);

    long startSeconds = startDateTime.toEpochSecond(java.time.ZoneOffset.UTC);
    long endSeconds = endDateTime.toEpochSecond(java.time.ZoneOffset.UTC);
    long randomSeconds = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds);

    return LocalDateTime.ofEpochSecond(randomSeconds, 0, java.time.ZoneOffset.UTC);
  }

  private String generateEventName(Random random) {
    List<String> eventNames = Arrays.asList("CLICK", "VIEW", "PURCHASE", "LOGIN", "LOGOUT");
    return eventNames.get(random.nextInt(eventNames.size()));
  }

  private Map<String, String> generateRandomMetadata(EventType eventType) {
    Map<String, String> metadata = new HashMap<>();
    switch (eventType) {
      case VIEW:
        metadata.put("os", "Windows 10");
        metadata.put("browser", "Chrome");
        break;
      case CLICK:
        metadata.put("action", "Button Clicked");
        metadata.put("target", "Add to Cart");
        break;
      case PURCHASE:
        metadata.put("currency", "USD");
        metadata.put("cartValue", "$200.00");
        break;
      case LOGIN:
        metadata.put("loginMethod", "Email");
        metadata.put("sessionStart", LocalDateTime.now().toString());
        break;
      case LOGOUT:
        metadata.put("logoutReason", "User clicked logout");
        metadata.put("sessionDuration", "3600");
        break;
      default:
        metadata.put("key", "value");
        break;
    }
    return metadata;
  }
}
