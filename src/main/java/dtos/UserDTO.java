package dtos;

import entities.Role;
import entities.User;

import java.util.List;
import java.util.Set;

public class UserDTO {
    private String username;
    private List<RoleDTO> roles;
    private List<MatchDTO> homeMatches;
    private List<MatchDTO> awayMatches;

    public UserDTO() {}

    public UserDTO(String username) {
        this.username = username;
    }

    public UserDTO(User user) {
        if (user != null) {
            this.username = user.getUsername();
            this.roles = RoleDTO.convertToDTO(user.getRoleList());
            this.homeMatches = MatchDTO.convertToDTO(user.getHomeMatches());
            this.awayMatches = MatchDTO.convertToDTO(user.getAwayMatches());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public List<MatchDTO> getHomeMatches() {
        return homeMatches;
    }

    public void setHomeMatches(List<MatchDTO> homeMatches) {
        this.homeMatches = homeMatches;
    }

    public List<MatchDTO> getAwayMatches() {
        return awayMatches;
    }

    public void setAwayMatches(List<MatchDTO> awayMatches) {
        this.awayMatches = awayMatches;
    }
}
