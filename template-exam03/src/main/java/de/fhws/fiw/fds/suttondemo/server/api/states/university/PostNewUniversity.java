package de.fhws.fiw.fds.suttondemo.server.api.states.university;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.University;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class PostNewUniversity extends AbstractPostState<Response, University> {

    public PostNewUniversity(ServiceContext serviceContext, University modelToStore) {
        super(serviceContext, modelToStore);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected NoContentResult saveModel() {
        return DaoFactory.getInstance().getUniversityDao().create(this.modelToStore);
    }

    @Override
    protected void defineTransitionLinks() {
        // Define transition links if necessary
    }
}