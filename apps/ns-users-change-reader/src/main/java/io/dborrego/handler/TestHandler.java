package io.dborrego.handler;

import java.util.List;
import java.util.stream.Collectors;

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
  public List<UserDTO> get(String id) {
    return userClient.getAllUsers().stream().filter(u -> u.getDni() != null && u.getDni().equals(id))
        .collect(Collectors.toList());
  }

}
