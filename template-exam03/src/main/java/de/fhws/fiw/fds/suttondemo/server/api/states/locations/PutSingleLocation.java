package de.fhws.fiw.fds.suttondemo.server.api.states.locations;

import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class PutSingleLocation extends AbstractPutState<Response, Location> {

    public PutSingleLocation(ServiceContext serviceContext, long requestedId, Location modelToUpdate) {
        super(serviceContext, requestedId, modelToUpdate);
    }


    @Override
    protected boolean clientDoesNotKnowCurrentModelState(AbstractModel modelFromDatabase) {
        return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
    }

    @Override
    protected void defineHttpCaching() {
        this.suttonResponse.cacheControl(CachingUtils.create30SecondsPublicCaching());
    }

    @Override protected SingleModelResult<Location> loadModel( )
    {
        return DaoFactory.getInstance( ).getLocationDao( ).readById( this.modelToUpdate.getId( ) );
    }

    @Override protected NoContentResult updateModel( )
    {
        return DaoFactory.getInstance( ).getLocationDao( ).update( this.modelToUpdate );
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( LocationUri.REL_PATH_ID, LocationRelTypes.GET_SINGLE_LOCATION, getAcceptRequestHeader( ),
                this.modelToUpdate.getId( ) );
    }
}
