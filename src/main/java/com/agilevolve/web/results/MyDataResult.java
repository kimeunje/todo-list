package com.agilevolve.web.results;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.agilevolve.domain.model.board.Board;
import com.agilevolve.domain.model.team.Team;
import com.agilevolve.domain.model.user.User;

public class MyDataResult {

  public static ResponseEntity<ApiResult> build(User user, List<Team> teams, List<Board> boards) {
    Map<String, Object> userData = new HashMap<>();
    userData.put("name", user.getUsername());

    List<TeamResult> teamResults = new ArrayList<>();
    for (Team team : teams) {
      teamResults.add(new TeamResult(team));
    }

    List<BoardResult> boardResults = new ArrayList<>();
    for (Board board : boards) {
      boardResults.add(new BoardResult(board));
    }

    ApiResult apiResult = ApiResult.blank()
        .add("user", userData)
        .add("teams", teamResults)
        .add("boards", boardResults);

    return Result.ok(apiResult);
  }

  public static class TeamResult {
    private long id;
    private String name;

    TeamResult(Team team) {
      this.id = team.getId().value();
      this.name = team.getName();
    }

    public long getId() {
      return id;
    }

    public String getName() {
      return name;
    }
  }

  public static class BoardResult {
    private long id;
    private String name;
    private String description;
    private long teamId;

    BoardResult(Board board) {
      this.id = board.getId().value();
      this.name = board.getName();
      this.description = board.getDescription();
      this.teamId = board.getTeamId().value();
    }

    public long getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getDescription() {
      return description;
    }

    public long getTeamId() {
      return teamId;
    }
  }
}
