package de.fhws.fiw.fds.suttondemo.server.api.services;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractJerseyService;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.api.states.locations.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("locations")
public class LocationJerseyService extends AbstractJerseyService {
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllLocations() {
        try {
            return new GetAllLocations( this.serviceContext, new GetAllLocations.AllLocations<>( ) ).execute( );
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleLocation(@PathParam("id") final long id) {
        try {
            return new GetSingleLocation( this.serviceContext, id ).execute( );
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createSingleLocation(final Location locationModel) {
        try {
            return new PostNewLocation( this.serviceContext, locationModel ).execute( );
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @PUT
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateSingleLocation(@PathParam("id") final long id, final Location locationModel) {
        try {
            return new PutSingleLocation( this.serviceContext, id, locationModel ).execute( );
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @DELETE
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteSingleLocation(@PathParam("id") final long id) {
        try {
            return new DeleteSingleLocation( this.serviceContext, id ).execute( );
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }
}
