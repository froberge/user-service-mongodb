package com.thecat.user.endpoint;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thecat.user.model.Status;
import com.thecat.user.model.User;

import org.bson.types.ObjectId;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @GET
    @Path( "/health" )
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "SUCCESS";
    }

    @GET
    @Path("/search/status/{status}")
    public List<User> searchByStatus(@PathParam("status") String status) {
        return User.findByStatus( status.toUpperCase() );
    }

    @GET
    @Path("/search/name/{name}")
    public User searchByName(@PathParam("name") String name) {
        User u = User.findByName(name);

        return ( u != null ) ? u : new User();
    }

    @GET
    public List<User> list() {
        return User.findAllUser();
    }

    @PUT
    @Path( "/addmyself" )
    @Produces(MediaType.TEXT_PLAIN)
    public Response addMyself() {
        User user = new User();
        user.name = "Felix";
        user.email = "felix@test.com";
        user.gender = "M";
        user.password = "password123";
        user.birthDate = LocalDate.of(2000, Month.JULY, 20);
        user.createDate = LocalDate.now();
        user.status = Status.ACTIVE;

        user.persist();

        return Response.ok().build();
    }

    @POST
    public Response addUser( User user ) {
        user.persist();
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        User user = User.findById(new ObjectId(id));
        if(user == null) {
            throw new NotFoundException();
        }
        user.delete();

        return Response.ok().build()
        ;
    }
}