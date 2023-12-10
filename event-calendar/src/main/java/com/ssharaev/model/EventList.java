package com.ssharaev.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

/**
 * Thread-safe list of events
 */
public class EventList {


    public static final TimePeriod THE_WHOLE_DAY = new TimePeriod( LocalTime.MIN, LocalTime.MAX );
    private final Map<LocalDate, Set<Event>> events = new ConcurrentHashMap<>();

    public int size() {
        return events.size();
    }

    public void add(Event event) {
        Set<Event> eventList = events.computeIfAbsent( event.getDate(), ( (date) -> new ConcurrentSkipListSet<>() ) );
        if ( !isSlotAvailable( eventList, event.getEventTime() ) ) {
            throw new RuntimeException( "Slot " + event.getDate() + event.getEventTime() + " is unavailable!" );
        }
        eventList.add( event );
        events.put( event.getDate(), eventList );
    }

    public void remove(Event event) {
        Set<Event> eventList = events.get( event.getDate() );
        if ( eventList != null ) {
            eventList.remove( event );
        }
    }

    public List<Event> getAllEvents() {
        return events.values().stream()
            .flatMap( Collection::stream )
            .collect( Collectors.toList());
    }

    public boolean isSlotAvailable(LocalDate date, TimePeriod timePeriod) {
        Set<Event> eventList = events.get( date );
        if ( eventList == null || eventList.isEmpty() ) {
            return true;
        }
        return isSlotAvailable( eventList, timePeriod );
    }

    private boolean isSlotAvailable(@NotNull Set<Event> eventList, @NotNull TimePeriod timePeriod) {

        boolean startDateIsNotInEvents = eventList.stream()
            .map( Event::getEventTime )
            .noneMatch( period -> period.timeInPeriod( timePeriod.getStartTime() ) );
        if ( !startDateIsNotInEvents ) {
            return false;
        }

        return eventList.stream()
            .map( Event::getEventTime )
            .noneMatch( period -> period.timeInPeriod( timePeriod.getEndTime() ) );
    }

    public List<TimePeriod> getAvailableSlotsForDate(LocalDate date) {
        Set<Event> eventSet = events.get( date );
        if ( eventSet == null || eventSet.isEmpty() ) {
            return List.of( THE_WHOLE_DAY );
        }
        List<Event> eventList = List.copyOf( eventSet );
        List<TimePeriod> result = new ArrayList<>();
        for ( int i = 0; i < eventList.size(); i++ ) {
            LocalTime currentEventEndTime = eventList.get( i ).getEventTime().getStartTime();
            LocalTime nextStartTimeOrMax = ( i == eventList.size() - 1 ) ? LocalTime.MAX :
                eventList.get( i ).getEventTime().getStartTime();
            if ( currentEventEndTime.isBefore( nextStartTimeOrMax ) ) {
                result.add( new TimePeriod( currentEventEndTime, nextStartTimeOrMax ) );
            }
        }
        return result;
    }
}
