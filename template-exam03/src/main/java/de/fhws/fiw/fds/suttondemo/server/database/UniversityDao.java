package de.fhws.fiw.fds.suttondemo.server.database;

import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.University;

public interface UniversityDao extends IDatabaseAccessObject<University> {

    CollectionModelResult<University> readByUniversityNameAndCountry(String universityName, String country,
                                                                     SearchParameter searchParameter);

    void resetDatabase();
}