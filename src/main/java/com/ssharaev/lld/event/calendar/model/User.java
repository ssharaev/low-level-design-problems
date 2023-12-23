package com.ssharaev.lld.event.calendar.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class User implements EventParticipant, Entity {

    private final String id;
    private final String name;
    private final TimePeriod workingHours;
    private final EventList participantEvents = new EventList();
    private Team team;

    // TODO synchronization
    @Override
    public boolean appointForAnEvent(Event event, int teamMembersCount) {
        if ( isAvailable( event ) ) {
            participantEvents.add( event );
            return true;
        }
        return false;
    }

    @Override
    public void clearEvent(Event event) {
        participantEvents.remove( event );
    }

    @Override
    public List<TimePeriod> getAvailableSlotsForDate(LocalDate eventDate, int teamMembersCount) {
        return participantEvents.getAvailableSlotsForDate( eventDate );
    }

    public boolean isAvailable(Event event) {
        return workingHours.periodInPeriod( event.getEventTime() )
            && participantEvents.isSlotAvailable(event.getDate(), event.getEventTime());
    }

    public void addTeam(Team team) {
        this.team = team;
    }
}
