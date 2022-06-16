package repository;

import dtos.MatchDTO;
import entities.*;
import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class MatchRepo {
    private static EntityManagerFactory emf;
    private static MatchRepo instance;

    private MatchRepo() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this repository.
     */
    public static MatchRepo getMatchRepo(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MatchRepo();
        }
        return instance;
    }

    public List<Match> getAllMatches() {
        EntityManager em = emf.createEntityManager();

        try  {
            TypedQuery<Match> query = em.createQuery("select m from Match m", Match.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Match> getMatchesByUser(User user) {
        EntityManager em = emf.createEntityManager();
        List<Match> matches;
        try {
            TypedQuery<Match> query = em.createQuery("select m from Match m where :user member of m.homeTeam or :user member of m.awayTeam", Match.class);
            query.setParameter("user", user);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Match getMatchById(Long id) {
        EntityManager em = emf.createEntityManager();
        Match match;
        try {
            match = em.find(Match.class, id);
        } finally {
            em.close();
        }

        if (match == null) throw new NotFoundException("Match with given id not found.");

        return match;
    }

    public List<MatchDTO> getMatchesByLocation(String location) {
        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Match> query =
                    em.createQuery("select m from Match m inner join Location l " +
                            "where l.address like :location or l.city like :location", Match.class);
            query.setParameter("location", "%"+location+"%");
            List<Match> matches = query.getResultList();
            return MatchDTO.convertToDTO(matches);
        } finally {
            em.close();
        }
    }

    public MatchDTO createMatch(MatchDTO matchDTO) {
        EntityManager em = emf.createEntityManager();

        Match match = new Match(matchDTO);

        try {
            em.getTransaction().begin();
            em.persist(match);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new MatchDTO(match);
    }

    private Match addUserToHomeTeam(Match match, User user) {
        EntityManager em = emf.createEntityManager();
        match.addUserToHomeTeam(user);;

        try {
            em.getTransaction().begin();
            em.merge(match);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return match;
    }

    private Match addUserToAwayTeam(Match match, User user) {
        EntityManager em = emf.createEntityManager();
        match.addUserToAwayTeam(user);;

        try {
            em.getTransaction().begin();
            em.merge(match);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return match;
    }

    public static void main(String[] args) throws AuthenticationException {

        MatchRepo matchRepo = MatchRepo.getMatchRepo(EMF_Creator.createEntityManagerFactory());
        UserRepo userRepo = UserRepo.getUserRepo(EMF_Creator.createEntityManagerFactory());

        Location location = new Location("Almarksvej 45", "Danmark");
        
        User spiller1 = new User("spiller1", "password1");
        User spiller2 = new User("spiller2", "password2");
        User spiller3 = new User("spiller3", "password3");
        User spiller4 = new User("spiller4", "password4");

        Match match = new Match("4b tennis ting", "Tennis", false, location);
        match.addUserToHomeTeam(spiller1);
        match.addUserToAwayTeam(spiller2);
        match.addUserToHomeTeam(spiller3);
        MatchDTO newMatch = matchRepo.createMatch(new MatchDTO(match));

        System.out.println(newMatch.getMatchName() + " " + newMatch.getHomeTeam().get(0).getUsername());

        List<Match> matchList = matchRepo.getMatchesByUser(spiller1);

        for (Match match1 : matchList) {
            System.out.println(match1.getMatchName());
        }

        List<Match> matchList2 = matchRepo.getMatchesByUser(spiller3);

        for (Match match1 : matchList2) {
            System.out.println(match1.getMatchName());
        }
    }
}
