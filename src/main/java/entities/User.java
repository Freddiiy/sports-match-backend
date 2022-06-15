package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Role> roleSet = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Team> teamSet = new HashSet<>();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public List<String> getRolesAsStrings() {
        if (roleSet.isEmpty()) return null;

        List<String> rolesAsStrings = new ArrayList<>();
        for (Role role : roleSet) {
            rolesAsStrings.add(role.getName());
        }
        return rolesAsStrings;
    }

    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, this.password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<Role> roles) {
        this.roleSet = roles;
    }

    public void addRole(String rolename) {
        Role role = new Role(rolename);
        this.roleSet.add(role);
    }

    public void addRole(Role role) {
        this.roleSet.add(role);
    }

    public Set<Team> getTeamSet() {
        return teamSet;
    }

    public void setTeamSet(Set<Team> teamSet) {
        this.teamSet = teamSet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }
}
