package de.fhws.fiw.fds.suttondemo.server.api.states.locations;

import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.api.states.persons.PersonRelTypes;
import de.fhws.fiw.fds.suttondemo.server.api.states.persons.PersonUri;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class DeleteSingleLocation extends AbstractDeleteState<Response, Location> {

    public DeleteSingleLocation(ServiceContext serviceContext, long modelIdToDelete) {
        super(serviceContext, modelIdToDelete);
    }


    @Override protected SingleModelResult<Location> loadModel( )
    {
        return DaoFactory.getInstance( ).getLocationDao( ).readById( this.modelIdToDelete );
    }

    @Override protected NoContentResult deleteModel( )
    {
        DaoFactory.getInstance( ).getPersonLocationDao( ).deleteRelationsToSecondary( this.modelIdToDelete );
        return DaoFactory.getInstance( ).getPersonDao( ).delete( this.modelIdToDelete );
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( PersonUri.REL_PATH, PersonRelTypes.GET_ALL_PERSONS, getAcceptRequestHeader( ) );
    }
}
