package com.jc.ipldashboard.controller;

import com.jc.ipldashboard.model.Team;
import com.jc.ipldashboard.repository.MatchRepository;
import com.jc.ipldashboard.repository.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/teams/{teamName}")
    public ResponseEntity<Team> getTeam(@PathVariable String teamName) {
        return teamRepository.findByTeamName(teamName).map(team -> {
            team.setMatches(matchRepository.findLatestMatchesByTeam(teamName, 4));
            return ResponseEntity.ok(team);
        }).orElse(ResponseEntity.notFound().build());
    }
}
