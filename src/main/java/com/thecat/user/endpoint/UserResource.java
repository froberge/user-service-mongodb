package com.thecat.user.endpoint;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger; 

import com.thecat.user.model.Status;
import com.thecat.user.model.User;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private static final Logger LOG = Logger.getLogger(UserResource.class);

    @GET
    @Path( "/health" )
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "SUCCESS";
    }

    @GET
    @Path("/search/status/{status}")
    public List<User> searchByStatus(@PathParam("status") String status) {
        return User.findByStatus( status );
    }

    @GET
    @Path("/search/name/{name}")
    public User searchByName(@PathParam("name") String name) {
        LOG.info( "searchByName" );
        User u = User.findByName(name);
        return ( u != null ) ? u : new User();
    }

    @GET
    public List<User> list() {
        LOG.info( "getAllUser" );
        return User.findAllUser();
    }

    @POST
    @Consumes( MediaType.APPLICATION_JSON)
    public int getUser( User user ) {
        LOG.info( "Get user rest endpoint[" + user.email + "] [" + user.password + "]" );

        // First look at if the username exist.
        User u = User.findByEmail( user.email );

        if ( u != null ) {
            LOG.info( "user status " + u.status );
            // Validate if the user is active
            if ( u.status.equals(Status.ACTIVE ) ) {
                if ( !u.password.isEmpty() && u.password.endsWith( user.password ) ) {
                    return javax.ws.rs.core.Response.Status.OK.getStatusCode();
                } else {
                    return javax.ws.rs.core.Response.Status.PARTIAL_CONTENT.getStatusCode();
                }
            } else {
                return javax.ws.rs.core.Response.Status.PARTIAL_CONTENT.getStatusCode();
            }
        } else {
            return javax.ws.rs.core.Response.Status.NO_CONTENT.getStatusCode();
        }
    }

    @GET
    @Path("/count")
    public long getTotalCount() {
        return User.totalUsers();
    }

    @GET
    @Path("/countactive")
    public long getTotalActive() {
        return User.totalUsersActive();
    }

    @GET
    @Path("/count/status/{status}")
    public long getTotalByStatus(@PathParam("status") String status) {
        return User.totalUsersByStatus(status);
    }

    @GET
    @Path("/firstpage")
    public List<User> getFirstPage() {
        PanacheQuery<User> allUser = User.findAll();
        return allUser.page(Page.ofSize(10)).list();
    }

    @GET
    @Path("/from/{min}/to/{max}")
    public List<User> getUsersByRange(@PathParam("min") int min, @PathParam("max") int max) {
        PanacheQuery<User> allUser = User.findAll();
        return allUser.range(min, max).list();
        
    }

    @GET
    @Path( "/initializeDatabase" ) 
    @Produces(MediaType.TEXT_PLAIN)
    public Response addMyself() {

        for(int i=0 ;i<=50; i++ ) {
            User user = new User();
            user.name = "User_" + i;
            user.email = user.name + "@test.com";
            user.gender = "M";
            user.password = "password123";
            user.birthDate = LocalDate.of(2000, Month.JULY, 20);
            user.createDate = LocalDate.now();
            user.status = Status.getRandomGender();

            user.persist();
        }

        return Response.ok().build();
    }

    @POST
    @Path( "/adduser" )
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