package com.ssharaev.event.calendar.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface EventParticipant {

    String getName();

    boolean appointForAnEvent(Event event, int teamMembersCount);

    void clearEvent(Event event);

    EventList getParticipantEvents();

    List<TimePeriod> getAvailableSlotsForDate(LocalDate eventDate, int teamMembersCount);

    // TODO implementation
    static List<TimePeriod> findAvailableSlots(Map<EventParticipant, List<TimePeriod>> slots, int teamMembersCount) {
        List<TimePeriod> result = new ArrayList<>();

        return result;
    }
}
