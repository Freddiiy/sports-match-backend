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

    public User getJudge() {
        return judge;
    }

}
