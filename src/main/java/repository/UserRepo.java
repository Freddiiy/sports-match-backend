package repository;

import dtos.UserDTO;
import entities.Role;
import entities.User;

import javax.persistence.*;

import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserRepo {

    private static EntityManagerFactory emf;
    private static UserRepo instance;

    private UserRepo() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this repository.
     */
    public static UserRepo getUserRepo(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserRepo();
        }
        return instance;
    }

    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            TypedQuery<User> query = em.createQuery("select u from User u where u.username = :username", User.class);
            query.setParameter("username", username);

            user = query.getSingleResult();
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public User getVerifiedUser(User user) throws AuthenticationException {
        return getVerifiedUser(user.getUsername(), user.getPassword());
    }

    public User registerUser(String username, String password) {
        EntityManager em = emf.createEntityManager();
        User user = new User(username, password);
        Role role = checkIfRoleExistThenCreateNew(Role.RoleNames.USER);
        int validate = 0;

        System.out.println(validate);

        user.addRole(role);
        try {
            TypedQuery<User> query = em.createQuery("select u from User u where u.username = :username", User.class);
            query.setParameter("username", user.getUsername());
            validate = query.getResultList().size();

            System.out.println(validate);

            if (validate > 0) {
                throw new AuthenticationException("User already exists");
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        System.out.println(validate);
        if (validate <= 0) {
            System.out.println("Wow im insdie");
             try {
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }

        return user;
    }

    public User getUser(String username) {
        EntityManager em = emf.createEntityManager();
        User user;

        try {
            TypedQuery<User> query = em.createQuery("select u from User u where u.username = :username", User.class);
            query.setParameter("username", username);

            user = query.getSingleResult();
        } finally {
            em.close();
        }

        return user;
    }

    public Role getRole(String roleName) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Role role;

        try {
            TypedQuery<Role> query = em.createQuery("select r from Role r where r.name = :roleName", Role.class);
            query.setParameter("roleName", roleName);
            role = query.getResultList().get(0);
            System.out.println("getRole " + role.getId());
        } finally {
            em.close();
        }

        if (role == null) throw new NotFoundException("No role with that name was found");

        return role;
    }

    public boolean roleExists(Role role) {
        EntityManager em = emf.createEntityManager();
        int count;
        try {
            TypedQuery<Role> query = em.createQuery("select r from Role r where r.name = :roleName", Role.class);
            query.setParameter("roleName", role.getName());
            count = query.getResultList().size();
        } finally {
            em.close();
        }
        return count > 0;
    }

    public Role checkIfRoleExistThenCreateNew(String roleName) {
        EntityManager em = emf.createEntityManager();
        Role checkRole = new Role(roleName);

        if (roleExists(checkRole)) {
            return getRole(roleName);
        }

        /*
        try {
            em.getTransaction().begin();
            em.persist(checkRole);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
         */

        return checkRole;
    }
}
