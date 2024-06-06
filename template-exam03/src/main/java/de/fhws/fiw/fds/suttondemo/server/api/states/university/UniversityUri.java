package de.fhws.fiw.fds.suttondemo.server.api.states.university;

import de.fhws.fiw.fds.suttondemo.Start;

public interface UniversityUri {

    String PATH_ELEMENT = "university";
    String REL_PATH = Start.CONTEXT_PATH + "/api/" + PATH_ELEMENT;
    String REL_PATH_ID = REL_PATH + "/{id}";

}