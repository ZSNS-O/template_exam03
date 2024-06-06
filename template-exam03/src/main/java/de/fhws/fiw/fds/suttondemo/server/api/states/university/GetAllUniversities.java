package de.fhws.fiw.fds.suttondemo.server.api.states.university;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.suttondemo.server.api.models.University;
import jakarta.ws.rs.core.Response;

public class GetAllUniversities extends AbstractGetCollectionState<Response, University> {

    public GetAllUniversities(ServiceContext serviceContext, AbstractQuery<Response, University> query) {
        super(serviceContext, query);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UniversityUri.REL_PATH, UniversityRelTypes.CREATE_UNIVERSITY, getAcceptRequestHeader());
    }
}