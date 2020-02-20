package edu.baylor.resources;

import edu.baylor.model.Person;
import edu.baylor.model.Team;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@Path("/team")
@ApplicationScoped
public class TeamResource {

    @PersistenceContext(unitName = "TeamPU")
    private EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Team> readAllTeams() throws Exception {
        return em.createNamedQuery("Team.findAll", Team.class)
                .getResultList();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{teamId}")
    public Team get(@PathParam("teamId") Long teamId) {
        return em.find(Team.class, teamId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(Team team) throws Exception {
        if (team.getId() != null) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity("Unable to create Team, id was already set.")
                    .build();
        }

        try {
            em.persist(team);
            em.flush();
        } catch (ConstraintViolationException cve) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(cve.getMessage())
                    .build();
        } catch (Exception e) {
            return Response
                    .serverError()
                    .entity(e.getMessage())
                    .build();
        }
        return Response
                .created(new URI("team/" + team.getId().toString()))
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{teamId}/{personId}")
    @Transactional
    public Response addTeamMember(@PathParam("teamId") Long teamId, @PathParam("personId") Long personToAddId, Team team) throws Exception {
        try {
            Team entity = em.find(Team.class, teamId);
//            Team entity = em.find(Team.class, team.getId());
            Person personToAdd = em.find(Person.class, personToAddId);

            if (null == entity) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("Team with id of " + teamId + " does not exist.")
                        .build();
            }
            if (null == personToAdd) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("Person with id of " + personToAddId + " does not exist.")
                        .build();
            }
            // Check if number of members if less than 3
            if(entity.getMembers().size() >= 3)
            {
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity("Team with id of " + teamId + " can't have more than three members.")
                        .build();
            }
            // Check if new member is not in any teams (including team with teamId):
            if( null!=personToAdd.getTeam() || null!=personToAdd.getLedTeam())
            {
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity("Person with id of " + teamId + " is already in the team.")
                        .build();
            }

            em.merge(team);
            //OR:
            List<Person> entityMembers = entity.getMembers();
            entityMembers.add(personToAdd);
            entity.setMembers(entityMembers);
            em.merge(entity);

            return Response
                    .ok(team)
                    .build();
        } catch (Exception e) {
            return Response
                    .serverError()
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{teamId}/{teamToAddId}")
    @Transactional
    public Response addTeamToWishSet(@PathParam("teamId") Long teamId, @PathParam("teamToAddId") Long teamToAddId, Team team) throws Exception {
        try {
            Team entity = em.find(Team.class, teamId);
//            Team entity = em.find(Team.class, team.getId());
            Team teamToAdd = em.find(Team.class, teamToAddId);

            if (null == entity) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("Team with id of " + teamId + " does not exist.")
                        .build();
            }
            if (null == teamToAdd) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("Team with id of " + teamId + " does not exist.")
                        .build();
            }
            // check if teams have same skill:
            if(!entity.getSkill().equals(teamToAdd.getSkill()))
            {
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity("Team with id of " + teamId + " and team with id of " + teamToAddId + " have different skills.")
                        .build();
            }

            em.merge(team);
            //OR:
            entity.setPreferredOpponent(teamToAdd);
            em.merge(entity);

            return Response
                    .ok(team)
                    .build();
        } catch (Exception e) {
            return Response
                    .serverError()
                    .entity(e.getMessage())
                    .build();
        }
    }
}
