package de.fhws.fiw.fds.suttondemo.server.api.states.university;

import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.suttondemo.server.api.models.University;
import de.fhws.fiw.fds.suttondemo.server.api.states.module.ModuleRelTypes;
import de.fhws.fiw.fds.suttondemo.server.api.states.module.ModuleUri;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class GetSingleUniversity extends AbstractGetState<Response, University> {

    public GetSingleUniversity(ServiceContext serviceContext, long requestedId) {
        super(serviceContext, requestedId);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<University> loadModel() {
        return DaoFactory.getInstance().getUniversityDao().readById(this.requestedId);
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

    @Override
    protected void defineTransitionLinks() {
        addLink(UniversityUri.REL_PATH_ID, UniversityRelTypes.UPDATE_SINGLE_UNIVERSITY, getAcceptRequestHeader(),
                this.requestedId);
        addLink(UniversityUri.REL_PATH_ID, UniversityRelTypes.DELETE_SINGLE_UNIVERSITY, getAcceptRequestHeader(),
                this.requestedId);
        addLink(ModuleUri.REL_PATH, ModuleRelTypes.CREATE_MODULE, getAcceptRequestHeader(),
                this.requestedId);
    }
}