package com.ssharaev;

/*
Capabilities
● Onboard Users(include working hours of the day)
● Users can be grouped into a team. A user can be part of only one team.
● Create an event with participants, start time and end time for a given day(assume
start and end time are of same day)
● Participants can be users or teams or both.
● User can participate in only 1 event at any given point in time
● When creating an event, user has an option to specify the number of required
representatives(lets call it N) from teams(assume that the number of required
representatives are same for all teams in an event)
● When a team is added, block the event against the N available members of the team.
If the N users are not available for any team in the event, the event can’t be created
(Eg. If N is 2, then block the event against 2 available users of each team involved in
the meeting).
Requirements
1. Create User
2. Create Team of users
3. Create Event for users/teams
4. Get events between a time range for a user. Include events for the given user which
were blocked as part of the team being a participant.
5. Suggest available slots for given list of users/teams for a given day
Bonus
1. Update event time - if it is not possible return the status accordingly
2. Support recurring event(time, start_date, end_date, frequency)
 */
public class Main {
    public static void main(String[] args) {
        System.out.println( "Hello world!" );
    }
}