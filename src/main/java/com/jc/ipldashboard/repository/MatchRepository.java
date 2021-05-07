package com.jc.ipldashboard.repository;

import com.jc.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

    @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) " +
            "and m.date between :startDate and :endDate order by m.date desc")
    List<Match> getMatchesByTeamBetweenDates(@Param("teamName") String teamName,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    /*List<Match> getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(String teamName1,
                                                                          LocalDate date1,
                                                                          LocalDate date2,
                                                                          String teamName2,
                                                                          LocalDate date3,
                                                                          LocalDate date4);*/

    default List<Match> findLatestMatchesByTeam(String teamName, int count) {
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }

    /*default List<Match> findMatchesInAPeriod(String team, LocalDate date1, LocalDate date2) {
        return getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(team, date1, date2, team, date1, date2);
    }*/

}
