package com.eventapp.backend.events;

import java.util.List;

public class EventPageResponse {

    private final List<EventDto> content;
    private final long totalElements;
    private final int totalPages;
    private final int size;
    private final int number;

    public EventPageResponse(List<EventDto> content, long totalElements, int totalPages, int size, int number) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.size = size;
        this.number = number;
    }

    public List<EventDto> getContent() {
        return content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getSize() {
        return size;
    }

    public int getNumber() {
        return number;
    }
}
