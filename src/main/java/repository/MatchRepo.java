package repository;

import entities.Match;
import errorhandling.NotFoundException;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.math.MathContext;
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

    public void createMatch(Match match) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(match);
            em.close();
        } finally {
            em.close();
        }
    }
}
