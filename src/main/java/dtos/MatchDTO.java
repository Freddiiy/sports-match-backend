package dtos;

public class MatchDTO {
    private String matchName;
    private TeamDTO homeTeam;
    private TeamDTO awayTeam;
    private UserDTO judge;
    private String sportsType;
    private boolean inDoors;
    private LocationDTO locationDTO;


    public MatchDTO(String matchName, TeamDTO homeTeam, TeamDTO awayTeam, UserDTO judge, String sportsType, boolean inDoors, LocationDTO locationDTO) {
        this.matchName = matchName;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.judge = judge;
        this.sportsType = sportsType;
        this.inDoors = inDoors;
        this.locationDTO = locationDTO;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public TeamDTO getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(TeamDTO homeTeam) {
        this.homeTeam = homeTeam;
    }

    public TeamDTO getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(TeamDTO awayTeam) {
        this.awayTeam = awayTeam;
    }

    public UserDTO getJudge() {
        return judge;
    }

    public void setJudge(UserDTO judge) {
        this.judge = judge;
    }

    public String getSportsType() {
        return sportsType;
    }

    public void setSportsType(String sportsType) {
        this.sportsType = sportsType;
    }

    public boolean isInDoors() {
        return inDoors;
    }

    public void setInDoors(boolean inDoors) {
        this.inDoors = inDoors;
    }

    public LocationDTO getLocationDTO() {
        return locationDTO;
    }

    public void setLocationDTO(LocationDTO locationDTO) {
        this.locationDTO = locationDTO;
    }
}
