package de.fhws.fiw.fds.suttondemo.server.api.states.person_locations;

import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class PutSingleLocationOfPerson extends AbstractPutRelationState<Response, Location> {

    public PutSingleLocationOfPerson(ServiceContext serviceContext, long primaryId, long requestedId, Location modelToUpdate) {
        super(serviceContext, primaryId, requestedId, modelToUpdate);
        this.suttonResponse = new JerseyResponse<>();
    }


    @Override
    protected boolean clientDoesNotKnowCurrentModelState(AbstractModel modelFromDatabase) {
        return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
    }

    @Override
    protected void defineHttpCaching() {
        this.suttonResponse.cacheControl(CachingUtils.create30SecondsPublicCaching());
    }

    @Override
    protected SingleModelResult<Location> loadModel() {
        return DaoFactory.getInstance().getLocationDao().readById(this.requestedId);
    }

    @Override
    protected NoContentResult updateModel() {
        return DaoFactory.getInstance().getPersonLocationDao().update(this.primaryId, this.modelToUpdate);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PersonLocationUri.REL_PATH_ID,
                PersonLocationRelTypes.GET_SINGLE_LOCATION,
                getAcceptRequestHeader(),
                this.primaryId, this.requestedId);
    }
}
