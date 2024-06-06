package de.fhws.fiw.fds.suttondemo.server.api.states.person_locations;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class DeleteSingleLocationOfPerson extends AbstractDeleteRelationState<Response, Location> {

    public DeleteSingleLocationOfPerson(ServiceContext serviceContext, long modelIdToDelete, long primaryId) {
        super(serviceContext, modelIdToDelete, primaryId);
        this.suttonResponse = new JerseyResponse<>();
    }


    @Override
    protected SingleModelResult<Location> loadModel() {
        return DaoFactory.getInstance().getPersonLocationDao().readById(this.primaryId, this.modelIdToDelete);
    }

    @Override
    protected NoContentResult deleteModel() {
        return DaoFactory.getInstance().getPersonLocationDao().deleteRelation(this.primaryId, this.modelIdToDelete);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PersonLocationUri.REL_PATH,
                PersonLocationRelTypes.GET_ALL_LINKED_LOCATIONS,
                getAcceptRequestHeader(),
                this.primaryId);
    }
}
