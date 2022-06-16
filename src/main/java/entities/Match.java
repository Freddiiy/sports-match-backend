package entities;

import dtos.MatchDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SPORTMATCH")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MATCH_ID", unique = true)
    private Long id;

    private String matchName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_HOME_MATCH", joinColumns = {@JoinColumn(name = "MATCH_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    private List<User> homeTeam = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_AWAY_MATCH", joinColumns = {@JoinColumn(name = "MATCH_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    private List<User> awayTeam = new ArrayList<>();

    @OneToOne
    private User judge;

    private String sportsType;
    private boolean inDoors;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    public Match() {}

    public Match(String matchName, List<User> homeTeam, List<User> awayTeam, User judge, String sportsType, boolean inDoors, Location location) {
        this.matchName = matchName;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.judge = judge;
        this.sportsType = sportsType;
        this.inDoors = inDoors;
        this.location = location;
    }

    public Match(String matchName, User judge, String sportsType, boolean inDoors, Location location) {
        this.matchName = matchName;
        this.judge = judge;
        this.sportsType = sportsType;
        this.inDoors = inDoors;
        this.location = location;
    }

    public Match(String matchName, String sportsType, boolean inDoors, Location location) {
        this.matchName = matchName;
        this.sportsType = sportsType;
        this.inDoors = inDoors;
        this.location = location;
    }

    public Match(MatchDTO matchDTO) {
        this.matchName = matchDTO.getMatchName();
        this.homeTeam = User.convertToEntity(matchDTO.getHomeTeam());
        this.awayTeam = User.convertToEntity(matchDTO.getAwayTeam());
        this.judge = new User(matchDTO.getJudge());
        this.sportsType = matchDTO.getSportType();
        this.inDoors = matchDTO.isInDoors();
        this.location = new Location(matchDTO.getLocation());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public List<User> getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(List<User> homeTeam) {
        this.homeTeam = homeTeam;
    }

    public List<User> getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(List<User> awayTeam) {
        this.awayTeam = awayTeam;
    }

    public User getJudge() {
        return judge;
    }

    public void setJudge(User judge) {
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void addUserToHomeTeam(User user) {
        this.homeTeam.add(user);
    }

    public void addUserToAwayTeam(User user) {
        this.awayTeam.add(user);
    }

    public static List<Match> convertToEntity(List<MatchDTO> dtos) {
        List<Match> matches = new ArrayList<>();
        for (MatchDTO dto : dtos) {
            matches.add(new Match(dto));
        }

        return matches;
    }
}
