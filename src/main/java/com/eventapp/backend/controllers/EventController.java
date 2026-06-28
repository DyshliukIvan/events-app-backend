package com.eventapp.backend.controllers;

import com.eventapp.backend.events.EventDto;
import com.eventapp.backend.events.EventPageResponse;
import com.eventapp.backend.models.Event;
import com.eventapp.backend.repositories.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public EventPageResponse getEvents(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);
        List<Event> filteredEvents;
        if (query != null && !query.isBlank()) {
            filteredEvents = eventRepository.findByTitleContainingIgnoreCase(query);
            return toResponse(filteredEvents, safePage, safeSize);
        }

        if (location != null && !location.isBlank()) {
            filteredEvents = eventRepository.findByLocationIgnoreCase(location);
            return toResponse(filteredEvents, safePage, safeSize);
        }

        Page<Event> events = eventRepository.findByStatusIgnoreCase("PUBLISHED", PageRequest.of(safePage, safeSize));
        return new EventPageResponse(
                events.getContent().stream().map(EventDto::new).toList(),
                events.getTotalElements(),
                events.getTotalPages(),
                events.getSize(),
                events.getNumber()
        );
    }

    @GetMapping("/{id}")
    public EventDto getEventById(@PathVariable Long id) {
        return eventRepository.findById(id)
                .map(EventDto::new)
                .orElseThrow(() -> new IllegalStateException("Event not found"));
    }

    private EventPageResponse toResponse(List<Event> events, int page, int size) {
        int fromIndex = Math.min(page * size, events.size());
        int toIndex = Math.min(fromIndex + size, events.size());
        List<EventDto> content = events.subList(fromIndex, toIndex)
                .stream()
                .map(EventDto::new)
                .toList();
        int totalPages = events.isEmpty() ? 0 : (int) Math.ceil((double) events.size() / size);

        return new EventPageResponse(content, events.size(), totalPages, size, page);
    }
}
