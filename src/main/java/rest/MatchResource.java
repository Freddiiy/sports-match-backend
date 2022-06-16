package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.MatchDTO;
import dtos.UserDTO;
import entities.Match;
import entities.User;
import errorhandling.API_Exception;
import org.glassfish.jersey.server.ResourceConfig;
import repository.MatchRepo;
import repository.UserRepo;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/matches")
public class MatchResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final MatchRepo matchRepo = MatchRepo.getMatchRepo(EMF);
    private final UserRepo userRepo = UserRepo.getUserRepo(EMF);
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
        Match match = GSON.fromJson(jsonString, Match.class);
        matchRepo.createMatch(match);

        return Response
                .ok()
                .build();
    }

    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add-to-home")
    public Response addToHome(String jsonString) throws API_Exception {
        String username;
        Long matchId;
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            username = jsonObject.get("username").getAsString();
            matchId = Long.parseLong(jsonObject.get("matchId").getAsString());

            System.out.println(username + " " + matchId);
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied",400,e);
        }
        User user = userRepo.getUser(username);
        matchRepo.addUserToHomeTeam(matchId, user);

        return Response
                .ok()
                .build();
    }

    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add-to-away")
    public Response addToAway(String jsonString) throws API_Exception {
        String username;
        Long matchId;
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            username = jsonObject.get("username").getAsString();
            matchId = Long.parseLong(jsonObject.get("matchId").getAsString());

            System.out.println(username + " " + matchId);
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied",400,e);
        }
        User user = userRepo.getUser(username);
        matchRepo.addUserToAwayTeam(matchId, user);

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
