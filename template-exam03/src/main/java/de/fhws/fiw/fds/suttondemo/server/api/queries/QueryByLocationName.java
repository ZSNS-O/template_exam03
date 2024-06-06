package de.fhws.fiw.fds.suttondemo.server.api.queries;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;

public class QueryByLocationName<R> extends AbstractRelationQuery<R, Location> {

    private String cityName;

    private int waitingTime;

    public QueryByLocationName(long primaryId, String cityName, int offset, int size) {
        super(primaryId);
        this.cityName = cityName;
        this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    protected CollectionModelResult<Location> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        return DaoFactory.getInstance().getPersonLocationDao().readByCityName(this.primaryId, this.cityName, searchParameter);
    }
}
