package de.fhws.fiw.fds.suttondemo.server.api.queries;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Module;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;

public class QueryByModuleNameAndSemesterOffered<R> extends AbstractQuery<R, Module> {

    private String moduleName;
    private int semesterOffered;

    public QueryByModuleNameAndSemesterOffered(String moduleName, int semesterOffered, int offset, int size) {
        this.moduleName = moduleName;
        this.semesterOffered = semesterOffered;
        this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getSemesterOffered() {
        return this.semesterOffered;
    }

    public void setSemesterOffered(int semesterOffered) {
        this.semesterOffered = semesterOffered;
    }

    protected CollectionModelResult<Module> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        return DaoFactory.getInstance().getModuleDao().readByModuleNameAndSemesterOffered(
                this.moduleName,
                this.semesterOffered,
                searchParameter);
    }
}