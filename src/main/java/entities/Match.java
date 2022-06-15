package entities;

import javax.persistence.*;

@Entity
@Table(name = "MATCH")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matchName;
    private String homeTeam;
    private String awayTeam;

    @ManyToOne
    private User judge;
    private String sportsType;
    private boolean inDoors;

    @OneToOne
    private Location location;

    public Match() {}

    public Match(String matchName, String homeTeam, String awayTeam, User judge, String sportsType, boolean inDoors, Location location) {
        this.matchName = matchName;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.judge = judge;
        this.sportsType = sportsType;
        this.inDoors = inDoors;
        this.location = location;
    }

    public User getJudge() {
        return judge;
    }

}
