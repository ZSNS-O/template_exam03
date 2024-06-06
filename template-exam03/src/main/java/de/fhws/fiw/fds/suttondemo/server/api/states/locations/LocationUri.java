package de.fhws.fiw.fds.suttondemo.server.api.states.locations;

import de.fhws.fiw.fds.suttondemo.Start;

public interface LocationUri
{
	String PATH_ELEMENT = "locations";
	String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
	String REL_PATH_ID = REL_PATH + "/{id}";
}
