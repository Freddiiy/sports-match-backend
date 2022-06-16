package dtos;

import entities.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO {
    public static class RoleNames {
        public static final String USER = "user";
        public static final String PLAYER = "player";
        public static final String ADMIN = "admin";
    }

    private String name;
    private List<UserDTO> userDTOList;

    public RoleDTO() {}

    public RoleDTO(Role role) {
        if (role != null) {
            this.name = role.getName();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDTO> getUserDTOList() {
        return userDTOList;
    }

    public void setUserDTOList(List<UserDTO> userDTOList) {
        this.userDTOList = userDTOList;
    }

    public static List<RoleDTO> convertToDTO(List<Role> entities) {
        List<RoleDTO> roleDTOS = new ArrayList<>();
        for (Role entity : entities) {
            roleDTOS.add(new RoleDTO(entity));
        }

        return roleDTOS;
    }
}
