package com.ssharaev.service;

import java.util.List;

import com.ssharaev.model.Team;
import com.ssharaev.model.TimePeriod;
import com.ssharaev.model.User;
import com.ssharaev.repository.TeamRepository;
import com.ssharaev.repository.UserRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ParticipantService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public User createUser(String id, String name, TimePeriod workingHours) {
        User user = new User( id, name, workingHours );
        userRepository.save( user );
        return user;
    }

    public Team createTeam(String id, String name, List<User> users) {
        Team team = new Team( id, name, users );
        teamRepository.save( team );
        return team;
    }

    public Team addUserToTeam(User user, Team team) {
        if (user.getTeam() != null) {
            throw new RuntimeException("User is already a member of a team " + user.getTeam().getName());
        }
        team.addUser( user );
        user.addTeam( team );
        userRepository.save( user );
        teamRepository.save( team );
        return team;
    }
}
