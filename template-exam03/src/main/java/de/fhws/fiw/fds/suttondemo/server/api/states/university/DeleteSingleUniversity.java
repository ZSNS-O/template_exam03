package de.fhws.fiw.fds.suttondemo.server.api.states.university;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.University;
import de.fhws.fiw.fds.suttondemo.server.api.states.persons.PersonRelTypes;
import de.fhws.fiw.fds.suttondemo.server.api.states.persons.PersonUri;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class DeleteSingleUniversity extends AbstractDeleteState<Response, University> {

    public DeleteSingleUniversity(ServiceContext serviceContext, long modelIdToDelete) {
        super(serviceContext, modelIdToDelete);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<University> loadModel() {
        return DaoFactory.getInstance().getUniversityDao().readById(this.modelIdToDelete);
    }

    @Override
    protected NoContentResult deleteModel() {
        return DaoFactory.getInstance().getUniversityDao().delete(this.modelIdToDelete);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UniversityUri.REL_PATH,
                UniversityRelTypes.GET_ALL_UNIVERSITIES,
                getAcceptRequestHeader());
    }

}