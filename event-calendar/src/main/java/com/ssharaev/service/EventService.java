package com.ssharaev.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ssharaev.model.Event;
import com.ssharaev.model.EventParticipant;
import com.ssharaev.model.TimePeriod;
import com.ssharaev.repository.EventRepository;
import lombok.AllArgsConstructor;

/*
3. Create Event for users/teams
4. Get events between a time range for a user. Include events for the given user which
   were blocked as part of the team being a participant.
5. Suggest available slots for given list of users/teams for a given day
 */
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Event createEvent(String id,
                             String eventTitle,
                             TimePeriod eventPeriod,
                             LocalDate eventDate,
                             List<EventParticipant> participants,
                             int membersCount) {
        Event event = new Event( id, eventTitle, null, eventDate, eventPeriod );
        for ( EventParticipant participant : participants ) {
            boolean result = participant.appointForAnEvent( event, membersCount );
            if ( !result ) {
                participants.forEach( part -> part.clearEvent( event ) );
                throw new RuntimeException(
                    "Unable to create event! Participant " + participant.getName() + " is busy!" );
            }
        }
        event.addParticipants( participants );
        eventRepository.save( event );
        return event;
    }


    public List<Event> getParticipantEvents(EventParticipant eventParticipant) {
        return eventParticipant.getParticipantEvents().getAllEvents();
    }

    public List<TimePeriod> suggestAvailableSlots(TimePeriod eventPeriod,
                                                  LocalDate eventDate,
                                                  List<EventParticipant> participants,
                                                  int membersCount) {

        Map<EventParticipant, List<TimePeriod>> participantSlots = participants.stream()
            .collect( Collectors.toMap( Function.identity(),
                eventParticipant -> eventParticipant.getAvailableSlotsForDate( eventDate, membersCount )) );
        return EventParticipant.findAvailableSlots( participantSlots, membersCount );

    }


}

