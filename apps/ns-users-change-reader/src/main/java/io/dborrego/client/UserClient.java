package io.dborrego.client;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/users")
@RegisterRestClient(configKey = "client-user")
public interface UserClient {

    @PUT
    @Path("/{id}")
    public void update(UserDTO user, @PathParam("id") Long id);

    @POST
    public void create(UserDTO user);

    @GET
    public List<UserDTO> getAllUsers();

    // @GET
    // public CompletionStage<List<UserDTO>> getAllUsers();

    // @GET
    // public Set<UserDTO> getByDni(@QueryParam("dni") String dni);

}
