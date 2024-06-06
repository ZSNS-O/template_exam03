package de.fhws.fiw.fds.suttondemo.server.api.states.person_locations;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class PostNewLocationOfPerson extends AbstractPostRelationState<Response, Location> {

    public PostNewLocationOfPerson(ServiceContext serviceContext, long primaryId, Location modelToStore) {
        super(serviceContext, primaryId, modelToStore);
        this.suttonResponse = new JerseyResponse<>();
    }


    @Override protected NoContentResult saveModel( )
    {
        return DaoFactory.getInstance( ).getPersonLocationDao( ).create( this.primaryId, this.modelToStore );
    }

    @Override protected void defineTransitionLinks( )
    {

    }
}
