package entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TEAM")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String teamName;

    @ManyToMany(mappedBy = "teamSet", cascade = CascadeType.PERSIST)
    private Set<User> userSet;

}
