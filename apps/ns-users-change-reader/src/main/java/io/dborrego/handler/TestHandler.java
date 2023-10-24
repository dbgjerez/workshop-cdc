package io.dborrego.handler;

import java.util.Set;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.dborrego.client.UserClient;
import io.dborrego.client.UserDTO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/test")
public class TestHandler {

  @RestClient
  UserClient userClient;

  @GET
  @Path("/{id}")
  public Set<UserDTO> get(String id) {
    return userClient.getById(id);
  }

}
