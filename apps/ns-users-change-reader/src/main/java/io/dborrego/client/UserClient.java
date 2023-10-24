package io.dborrego.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/users")
@RegisterRestClient(configKey = "client-user")
public interface UserClient {

    @PUT
    public void update(UserDTO user, @QueryParam("id") String dni);

    @POST
    public void create(UserDTO user);

}
