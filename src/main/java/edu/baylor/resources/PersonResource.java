/***************************************
 *
 * Author       : Askar Kuvanychbekov
 * Assignment   : II - mini app
 * Class        : CSI 5354
 *
 ***************************************/

package edu.baylor.resources;

//import edu.baylor.model.Car;
import edu.baylor.model.Person;

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

@Path("/person")
@ApplicationScoped
public class PersonResource {

    @PersistenceContext(unitName = "TeamPU")
    private EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Person> all() throws Exception {
        return em.createNamedQuery("Person.findAll", Person.class)
                .getResultList();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{personId}")
    public Person get(@PathParam("personId") Long personId) {
        return em.find(Person.class, personId);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{personId}")
    @Transactional
    public Response remove(@PathParam("personId") Long personId) throws Exception {
        try {
            Person entity = em.find(Person.class, personId);
            // cannot delete Person already in Team:
            if(entity.getTeam() != null) {
                em.remove(entity);
            }
            else {
                throw new Exception("Cannot delete a person in a team");
            }
        } catch (Exception e) {
            return Response
                    .serverError()
                    .entity(e.getMessage())
                    .build();
        }

        return Response
                .noContent()
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(Person person) throws Exception {
        if (person.getId() != null) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity("Unable to create Person, id was already set.")
                    .build();
        }

        try {
            em.persist(person);
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
                .created(new URI("person/" + person.getId().toString()))
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{personId}")
    @Transactional
    public Response update(@PathParam("personId") Long personId, Person person) throws Exception {
        try {
            Person entity = em.find(Person.class, personId);

            if (null == entity) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("Person with id of " + personId + " does not exist.")
                        .build();
            }

            em.merge(person);

            return Response
                    .ok(person)
                    .build();
        } catch (Exception e) {
            return Response
                    .serverError()
                    .entity(e.getMessage())
                    .build();
        }
    }
}
