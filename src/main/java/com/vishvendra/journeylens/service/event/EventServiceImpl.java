package com.vishvendra.journeylens.service.event;

import com.vishvendra.journeylens.entities.Event;
import com.vishvendra.journeylens.entities.Session;
import com.vishvendra.journeylens.repository.EventRepository;
import com.vishvendra.journeylens.repository.SessionRepository;
import com.vishvendra.journeylens.utils.event.EventMapperUtil;
import com.vishvendra.journeylens.utils.event.EventValidatorUtil;
import com.vishvendra.journeylens.utils.model.EventRequestDto;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("eventService")
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final SessionRepository sessionRepository;
  private final EventRepository eventRepository;
  private final EventMapperUtil eventMapperUtil;
  private final EventValidatorUtil eventValidatorUtil;

  @Value("${SESSION_DURATION_IN_SECONDS:10}")
  private Long sessionDurationInSeconds;

  @Override
  @Transactional
  public Event createEvent(EventRequestDto eventRequestDto) {
    this.eventValidatorUtil.validateRequest(eventRequestDto);
    LocalDateTime currentTime = LocalDateTime.now();
    Optional<Session> activeSession = this.sessionRepository.findActiveSession(
        eventRequestDto.getUserId(),
        currentTime);

    Session session;
    if (activeSession.isPresent()) {
      session = activeSession.get();
    } else {
      session = Session.builder()
          .userId(eventRequestDto.getUserId())
          .startTime(currentTime)
          .endTime(currentTime.plusSeconds(sessionDurationInSeconds))
          .build();
      this.sessionRepository.save(session);
    }
    Event.EventBuilder event = this.eventMapperUtil.mapAndReturnBuilder(eventRequestDto);
    event.session(session);
    return this.eventRepository.save(event.build());
  }

}
