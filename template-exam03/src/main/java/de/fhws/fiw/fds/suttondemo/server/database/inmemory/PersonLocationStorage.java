package de.fhws.fiw.fds.suttondemo.server.database.inmemory;

import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.inmemory.AbstractInMemoryRelationStorage;
import de.fhws.fiw.fds.sutton.server.database.inmemory.InMemoryPaging;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Location;
import de.fhws.fiw.fds.suttondemo.server.database.LocationDao;
import de.fhws.fiw.fds.suttondemo.server.database.PersonLocationDao;

public class PersonLocationStorage extends AbstractInMemoryRelationStorage<Location> implements PersonLocationDao {
    final private LocationDao locationStorage;

    public PersonLocationStorage(LocationDao locationStorage) {
        this.locationStorage = locationStorage;
    }

    @Override
    protected IDatabaseAccessObject<Location> getSecondaryStorage() {
        return this.locationStorage;
    }

    @Override
    public CollectionModelResult<Location> readByCityName(long primaryId, String cityName, SearchParameter searchParameter) {
        return InMemoryPaging.page(
                this.readAllLinkedByPredicate(primaryId, (p) -> cityName.isEmpty() || p.getCityName().equals(cityName)),
                searchParameter.getOffset(), searchParameter.getSize()
        );
    }

    @Override
    public CollectionModelResult<Location> readAllLinked(long primaryId, SearchParameter searchParameter) {
        return InMemoryPaging.page(
                this.readAllLinkedByPredicate(primaryId, (p) -> true),
                searchParameter.getOffset(), searchParameter.getSize()
        );
    }

    @Override
    public void resetDatabase() {

    }

    @Override
    public void initializeDatabase() {

    }
}
