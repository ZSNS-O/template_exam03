package de.fhws.fiw.fds.suttondemo.server.database.inmemory;

import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Module;
import de.fhws.fiw.fds.suttondemo.server.database.ModuleDao;

import java.util.function.Predicate;

public class ModuleStorage extends AbstractInMemoryStorage<Module> implements ModuleDao {

    @Override
    public CollectionModelResult<Module> readByModuleNameAndSemesterOffered(String moduleName, int semesterOffered, SearchParameter searchParameter) {
        return InMemoryPaging.page(this.readAllByPredicate(
                byModuleNameAndSemesterOffered(moduleName, semesterOffered),
                searchParameter
        ), searchParameter.getOffset(), searchParameter.getSize());
    }

    public void resetDatabase() {
        this.storage.clear();
    }

    private Predicate<Module> byModuleNameAndSemesterOffered(String moduleName, int semesterOffered) {
        return m -> (moduleName.isEmpty() || m.getModuleName().equals(moduleName)) &&
                (semesterOffered == 0 || m.getSemesterOffered() == semesterOffered);
    }
}
