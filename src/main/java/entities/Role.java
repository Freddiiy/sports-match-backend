package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Plaul
 */
@Entity
@Table(name = "ROLE")
public class Role implements Serializable {
    public static class RoleNames {
        public static final String USER = "user";
        public static final String PLAYER = "player";
        public static final String ADMIN = "admin";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String name;

    @ManyToMany(mappedBy = "roleSet", cascade = CascadeType.PERSIST)
    private Set<User> userSet;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}