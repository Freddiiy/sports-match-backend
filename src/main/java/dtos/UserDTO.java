package dtos;

import entities.Role;
import entities.User;

import java.util.Set;

public class UserDTO {
    private String username;

    public UserDTO(String username) {
        this.username = username;
    }

    public UserDTO(User user) {
        if (user != null) {
            username = user.getUsername();
        }
    }
}
