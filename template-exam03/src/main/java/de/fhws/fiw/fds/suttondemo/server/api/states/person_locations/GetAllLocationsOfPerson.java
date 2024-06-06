package de.fhws.fiw.fds.suttondemo.server.api.states.person_locations;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionRelationState;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import jakarta.ws.rs.core.Response;

public class GetAllLocationsOfPerson extends AbstractGetCollectionRelationState<Response, Location> {

    public GetAllLocationsOfPerson(ServiceContext serviceContext, long primaryId, AbstractRelationQuery<Response, Location> query) {
        super(serviceContext, primaryId, query);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PersonLocationUri.REL_PATH,
                PersonLocationRelTypes.CREATE_LOCATION,
                getAcceptRequestHeader(),
                this.primaryId);


        addLink(PersonLocationUri.REL_PATH_SHOW_ALL,
                PersonLocationRelTypes.GET_ALL_LOCATIONS,
                getAcceptRequestHeader(),
                this.primaryId);

    }
}
