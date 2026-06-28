package com.eventapp.backend.events;

import com.eventapp.backend.models.Event;

public class EventDto {

    private final Long id;
    private final String title;
    private final String description;
    private final String startDate;
    private final String endDate;
    private final String location;
    private final Integer capacity;
    private final String type;
    private final String status;
    private final Long creatorId;
    private final String creatorName;
    private final Long organizationId;
    private final String organizationName;
    private final Long registrationCount;
    private final Long followCount;
    private final String createdAt;
    private final String updatedAt;

    public EventDto(Event event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.startDate = event.getStartDate() == null ? null : event.getStartDate().toString();
        this.endDate = event.getEndDate() == null ? null : event.getEndDate().toString();
        this.location = event.getLocation();
        this.capacity = event.getCapacity();
        this.type = event.getType();
        this.status = event.getStatus();
        this.creatorId = event.getCreatorId();
        this.creatorName = event.getCreatorName();
        this.organizationId = event.getOrganizationId();
        this.organizationName = event.getOrganizationName();
        this.registrationCount = event.getRegistrationCount();
        this.followCount = event.getFollowCount();
        this.createdAt = event.getCreatedAt() == null ? null : event.getCreatedAt().toString();
        this.updatedAt = event.getUpdatedAt() == null ? null : event.getUpdatedAt().toString();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public Long getRegistrationCount() {
        return registrationCount;
    }

    public Long getFollowCount() {
        return followCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
