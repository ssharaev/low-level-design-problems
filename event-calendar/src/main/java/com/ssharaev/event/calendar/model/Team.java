package com.ssharaev.event.calendar.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class Team implements EventParticipant, Entity {

    private final String id;
    private final String name;
    private final Set<User> userList = new HashSet<>();

    public Team(String id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.userList.addAll( users );
    }

    // TODO synchronization
    @Override
    public boolean appointForAnEvent(Event event, int teamMembersCount) {
        int userCount = 0;
        for ( User user : userList ) {
            if ( user.appointForAnEvent( event, teamMembersCount ) ) {
                userCount++;
            }
            if (userCount == teamMembersCount) {
                break;
            }
        }
        if ( userCount < teamMembersCount ) {
            userList.forEach( user -> user.clearEvent( event ) );
            return false;
        }
        return true;
    }

    @Override
    public void clearEvent(Event event) {
        userList.forEach( user -> user.clearEvent( event ) );
    }

    @Override
    public EventList getParticipantEvents() {
        return null;
    }

    @Override
    public List<TimePeriod> getAvailableSlotsForDate(LocalDate eventDate, int teamMembersCount) {
        Map<EventParticipant, List<TimePeriod>> userSlots = userList.stream()
            .collect( Collectors.toMap(Function.identity(),
                user -> user.getAvailableSlotsForDate( eventDate, teamMembersCount ) ));

        return EventParticipant.findAvailableSlots( userSlots, teamMembersCount );
    }

    public void addUser(@NotNull User user) {
        if ( userList.contains(user) ) {
            return;
        }
        user.addTeam( this );
        this.userList.add( user );
    }
}
