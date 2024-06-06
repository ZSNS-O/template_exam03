package de.fhws.fiw.fds.suttondemo.server.api.services;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.Exceptions.SuttonWebAppException;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractJerseyService;
import de.fhws.fiw.fds.suttondemo.server.api.models.Module;
import de.fhws.fiw.fds.suttondemo.server.api.queries.QueryByModuleNameAndSemesterOffered;
import de.fhws.fiw.fds.suttondemo.server.api.states.module.DeleteSingleModule;
import de.fhws.fiw.fds.suttondemo.server.api.states.module.GetAllModules;
import de.fhws.fiw.fds.suttondemo.server.api.states.module.GetSingleModule;
import de.fhws.fiw.fds.suttondemo.server.api.states.module.PutSingleModule;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import  de.fhws.fiw.fds.suttondemo.server.api.states.module.PostNewModule;

@Path("modules")
public class ModuleJerseyService extends AbstractJerseyService {

    public ModuleJerseyService() {
        super();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllModules(
            @DefaultValue("") @QueryParam("modulename") final String moduleName,
            @DefaultValue("0") @QueryParam("semesterOffered") final int semesterOffered,
            @DefaultValue("0") @QueryParam("offset") int offset,
            @DefaultValue("20") @QueryParam("size") int size) {
        try {
            return new GetAllModules(
                    this.serviceContext,
                    new QueryByModuleNameAndSemesterOffered<>(moduleName, semesterOffered, offset, size)
            ).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(e.getExceptionMessage(), e.getStatus().getCode());
        }
    }

    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSingleModule(@PathParam("id") final long id) {
        try {
            return new GetSingleModule(this.serviceContext, id).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response
                    .status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build()
            );
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createSingleModule(final Module moduleModel) {
        try {
            return new PostNewModule(this.serviceContext, moduleModel).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @PUT
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateSingleModule(@PathParam("id") final long id, final Module moduleModel) {
        try {
            return new PutSingleModule(this.serviceContext, id, moduleModel).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }

    @DELETE
    @Path("{id: \\d+}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteSingleModule(@PathParam("id") final long id) {
        try {
            return new DeleteSingleModule(this.serviceContext, id).execute();
        } catch (SuttonWebAppException e) {
            throw new WebApplicationException(Response.status(e.getStatus().getCode())
                    .entity(e.getExceptionMessage()).build());
        }
    }
}