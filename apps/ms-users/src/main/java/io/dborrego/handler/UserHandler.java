package io.dborrego.handler;

import java.util.List;

import io.dborrego.domain.User;
import io.dborrego.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserHandler {

    @Inject
    UserService usersService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> list(@QueryParam("dni") String dni) {
        return usersService.listAll();
    }

    @GET
    @Path("/{id}")
    public User get(Long id) {
        return usersService.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User create(User user) {
        return usersService.create(user);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(User user, @PathParam(value = "id") Long id) {
        try {
            usersService.update(user, id);
        } catch (Exception e) {
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(Long id) {
        if (usersService.deleteById(id))
            return Response.ok().build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }

}