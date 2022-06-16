package dtos;

import entities.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchDTO {
    private String matchName;
    private List<UserDTO> homeTeam;
    private List<UserDTO> awayTeam;
    private UserDTO judge;
    private String sportType;
    private boolean inDoors;
    private LocationDTO location;

    public MatchDTO() {}

    public MatchDTO(Match match) {
        if (match != null) {
            this.matchName = match.getMatchName();
        }
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public List<UserDTO> getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(List<UserDTO> homeTeam) {
        this.homeTeam = homeTeam;
    }

    public List<UserDTO> getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(List<UserDTO> awayTeam) {
        this.awayTeam = awayTeam;
    }

    public UserDTO getJudge() {
        return judge;
    }

    public void setJudge(UserDTO judge) {
        this.judge = judge;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public boolean isInDoors() {
        return inDoors;
    }

    public void setInDoors(boolean inDoors) {
        this.inDoors = inDoors;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public static List<MatchDTO> convertToDTO(List<Match> entities) {
        List<MatchDTO> matchDTOS = new ArrayList<>();
        for (Match entity : entities) {
            matchDTOS.add(new MatchDTO(entity));
        }

        return matchDTOS;
    }
}
