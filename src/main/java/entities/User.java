package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import org.mindrot.jbcrypt.BCrypt;
import repository.UserRepo;
import utils.EMF_Creator;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true)
    private Long id;

    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE", joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    private List<Role> roleList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Match> matches = new ArrayList<>();


    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) return null;

        List<String> rolesAsStrings = new ArrayList<>();
        for (Role role : roleList) {
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

    public List<Role> getRoleSet() {
        return this.roleList;
    }

    public void setRoleList(List<Role> roles) {
        this.roleList = roles;
    }

    public void addRole(String roleName) {
        addRole(new Role(roleName));
    }

    public void addRole(Role role) {
        UserRepo userRepo = UserRepo.getUserRepo(EMF_Creator.createEntityManagerFactory());

        Role verifiedRole = userRepo.checkIfRoleExistThenCreateNew(role.getName());
        this.roleList.add(verifiedRole);
    }

    public void addMatch(Match match) {
        this.matches.add(match);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }
}
