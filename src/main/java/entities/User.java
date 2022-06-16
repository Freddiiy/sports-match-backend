package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import dtos.MatchDTO;
import dtos.RoleDTO;
import dtos.UserDTO;
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

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Match> homeMatches = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Match> awayMatches = new ArrayList<>();


    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User(UserDTO userDTO) {
        this.username = userDTO.getUsername();
        this.roleList = Role.convertToEntity(userDTO.getRoles());
        this.awayMatches = Match.convertToEntity(userDTO.getAwayMatches());
        this.homeMatches = Match.convertToEntity(userDTO.getHomeMatches());
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

    public List<Role> getRoleList() {
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

    public List<Match> getHomeMatches() {
        return homeMatches;
    }

    public List<Match> getAwayMatches() {
        return awayMatches;
    }

    public void addToHomeMatch(Match match) {
        this.homeMatches.add(match);
    }

    public void addToAwayMatch(Match match) {
        this.awayMatches.add(match);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }

    public static List<User> convertToEntity(List<UserDTO> dtos) {
        List<User> matches = new ArrayList<>();
        for (UserDTO dto : dtos) {
            matches.add(new User(dto));
        }

        return matches;
    }
}
