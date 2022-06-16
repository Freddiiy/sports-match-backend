package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MatchDTO;
import entities.Match;
import entities.User;
import errorhandling.API_Exception;
import org.glassfish.jersey.server.ResourceConfig;
import repository.MatchRepo;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/matches")
public class MatchResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final MatchRepo matchRepo = MatchRepo.getMatchRepo(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMatches() {
        List<Match> matches;
        matches = matchRepo.getAllMatches();

        return Response
                .ok()
                .entity(GSON.toJson(matches))
                .build();
    }

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{user}")
    public Response getMatchesByUsername(@PathParam("user") String user) {
        return null;
    }

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/id/{id}")
    public Response getMatchById(@PathParam("id") Long id) {
        Match match = matchRepo.getMatchById(id);

        return Response
                .ok()
                .entity(GSON.toJson(match))
                .build();
    }

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/location/{location}")
    public Response getMatchesByLocation(@PathParam("location") String location) {
        List<MatchDTO> matches = matchRepo.getMatchesByLocation(location);

        return Response
                .ok()
                .entity(GSON.toJson(matches))
                .build();
    }

    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response addMatch(String jsonString) {
        MatchDTO match = GSON.fromJson(jsonString, MatchDTO.class);
        matchRepo.createMatch(match);

        return Response
                .ok()
                .build();
    }

    public static void main(String[] args) {
        MatchRepo matchRepo = MatchRepo.getMatchRepo(EMF_Creator.createEntityManagerFactory());
        List<Match> matches = matchRepo.getAllMatches();

        for (Match theMatch : matches) {
            System.out.println(theMatch.getMatchName());
        }
    }

}
