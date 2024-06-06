package de.fhws.fiw.fds.suttondemo.server.database;

import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Module;

public interface ModuleDao extends IDatabaseAccessObject<Module> {

    CollectionModelResult<Module> readByModuleNameAndSemesterOffered(String moduleName, int semesterOffered,
                                                                     SearchParameter searchParameter);

    void resetDatabase();
}