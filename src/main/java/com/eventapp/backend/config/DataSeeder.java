package com.eventapp.backend.config;

import com.eventapp.backend.models.Event;
import com.eventapp.backend.repositories.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedEvents(EventRepository eventRepository) {
        return args -> {
            List<Event> existingEvents = eventRepository.findAll();
            List<Event> eventsWithoutStatus = existingEvents.stream()
                    .filter(event -> event.getStatus() == null || event.getStatus().isBlank())
                    .peek(event -> event.setStatus("PUBLISHED"))
                    .toList();

            if (!eventsWithoutStatus.isEmpty()) {
                eventRepository.saveAll(eventsWithoutStatus);
            }

            {
                Event concert = new Event(
                        "Prague Summer Concert",
                        "Live music evening with local bands and outdoor food stands.",
                        LocalDateTime.of(2026, 7, 12, 19, 0),
                        LocalDateTime.of(2026, 7, 12, 22, 30),
                        "Prague",
                        350,
                        "MUSIC"
                );
                concert.setCreatorName("EventApp Team");
                concert.setRegistrationCount(124L);
                concert.setFollowCount(260L);
                if (!eventRepository.existsByTitleIgnoreCase(concert.getTitle())) {
                    eventRepository.save(concert);
                }

                Event festival = new Event(
                        "Brno Open Air Festival",
                        "Weekend city festival with workshops, talks and evening performances.",
                        LocalDateTime.of(2026, 8, 15, 10, 0),
                        LocalDateTime.of(2026, 8, 16, 23, 0),
                        "Brno",
                        900,
                        "FESTIVAL"
                );
                festival.setCreatorName("Brno Events");
                festival.setRegistrationCount(421L);
                festival.setFollowCount(880L);
                if (!eventRepository.existsByTitleIgnoreCase(festival.getTitle())) {
                    eventRepository.save(festival);
                }

                Event conference = new Event(
                        "Ostrava Tech Conference",
                        "One-day conference focused on backend engineering, cloud and mobile development.",
                        LocalDateTime.of(2026, 9, 20, 9, 0),
                        LocalDateTime.of(2026, 9, 20, 17, 30),
                        "Ostrava",
                        180,
                        "CONFERENCE"
                );
                conference.setCreatorName("Tech Hub Ostrava");
                conference.setRegistrationCount(89L);
                conference.setFollowCount(140L);
                if (!eventRepository.existsByTitleIgnoreCase(conference.getTitle())) {
                    eventRepository.save(conference);
                }

                Event jazzNight = new Event(
                        "Jazz Night Prague",
                        "Small club event with live jazz, reserved seating and a late-night jam session.",
                        LocalDateTime.of(2026, 10, 25, 20, 0),
                        LocalDateTime.of(2026, 10, 26, 0, 30),
                        "Prague",
                        80,
                        "MUSIC"
                );
                jazzNight.setCreatorName("Old Town Jazz Club");
                jazzNight.setRegistrationCount(63L);
                jazzNight.setFollowCount(97L);
                if (!eventRepository.existsByTitleIgnoreCase(jazzNight.getTitle())) {
                    eventRepository.save(jazzNight);
                }
            }
        };
    }
}
