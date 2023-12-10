package com.ssharaev.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class Event implements Entity, Comparable<Event> {

    private final String id;
    private final String title;
    private final String description;
    private final LocalDate date;
    private final TimePeriod eventTime;
    private final List<EventParticipant> eventParticipants = new ArrayList<>();

    public void addParticipants(List<EventParticipant> participants) {
        this.eventParticipants.addAll( participants );
    }

    @Override
    public int compareTo(@NotNull Event o) {
        int result = date.compareTo( o.date );
        if ( result != 0 ) {
            return result;
        }
        return eventTime.compareTo( o.eventTime );

    }
}
