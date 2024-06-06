package de.fhws.fiw.fds.suttondemo.server.api.states.dispatcher;

import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetDispatcherState;
import de.fhws.fiw.fds.suttondemo.server.api.states.module.ModuleRelTypes;
import de.fhws.fiw.fds.suttondemo.server.api.states.module.ModuleUri;
import jakarta.ws.rs.core.Response;

public class GetModuleDispatcher extends AbstractGetDispatcherState<Response> {

    public GetModuleDispatcher(ServiceContext serviceContext) {
        super(serviceContext);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(ModuleUri.REL_PATH, ModuleRelTypes.GET_ALL_MODULES, getAcceptRequestHeader());
        addLink(ModuleUri.REL_PATH, ModuleRelTypes.CREATE_MODULE, getAcceptRequestHeader());
    }
}
