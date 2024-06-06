package de.fhws.fiw.fds.suttondemo.server.api.states.locations;


import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class PostNewLocation extends AbstractPostState<Response, Location>
{
	public PostNewLocation(ServiceContext serviceContext, Location modelToStore) {
		super(serviceContext, modelToStore);
	}


	@Override protected NoContentResult saveModel( )
	{
		return DaoFactory.getInstance( ).getLocationDao( ).create( this.modelToStore );
	}

	@Override protected void defineTransitionLinks( )
	{

	}
}
