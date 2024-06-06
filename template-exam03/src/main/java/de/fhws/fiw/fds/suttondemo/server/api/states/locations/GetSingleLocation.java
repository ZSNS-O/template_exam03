package de.fhws.fiw.fds.suttondemo.server.api.states.locations;

import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.api.states.persons.PersonUri;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class GetSingleLocation extends AbstractGetState<Response, Location> {

    public GetSingleLocation(ServiceContext serviceContext, long requestedId) {
        super(serviceContext, requestedId);
    }


    @Override protected SingleModelResult<Location> loadModel( )
    {
        return DaoFactory.getInstance( ).getLocationDao( ).readById( this.requestedId );
    }

    @Override
    protected boolean clientKnowsCurrentModelState(AbstractModel modelFromDatabase) {
        return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
    }

    @Override
    protected void defineHttpCaching() {
        final String eTagOfModel = EtagGenerator.createEtag(this.requestedModel.getResult());
        this.suttonResponse.entityTag(eTagOfModel);
        this.suttonResponse.cacheControl(CachingUtils.create30SecondsPublicCaching());
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( LocationUri.REL_PATH_ID, LocationRelTypes.UPDATE_SINGLE_LOCATION, getAcceptRequestHeader( ),
                this.requestedId );
        addLink( PersonUri.REL_PATH_ID, LocationRelTypes.DELETE_SINGLE_LOCATION, getAcceptRequestHeader( ),
                this.requestedId );
    }
}
