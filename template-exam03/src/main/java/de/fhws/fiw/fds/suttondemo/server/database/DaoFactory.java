

package de.fhws.fiw.fds.suttondemo.server.database;

import de.fhws.fiw.fds.suttondemo.server.database.inmemory.LocationStorage;
import de.fhws.fiw.fds.suttondemo.server.database.inmemory.ModuleStorage;
import de.fhws.fiw.fds.suttondemo.server.database.inmemory.PersonLocationStorage;
import de.fhws.fiw.fds.suttondemo.server.database.inmemory.PersonStorage;
import de.fhws.fiw.fds.suttondemo.server.database.inmemory.UniversityStorage;

public class DaoFactory {

    private static DaoFactory INSTANCE;

    public static DaoFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DaoFactory();
        }

        return INSTANCE;
    }

    private final PersonDao personDao;
    private final LocationDao locationDao;
    private final PersonLocationDao personLocationDao;
    private final UniversityDao universityDao;
    private final ModuleDao moduleDao;

    private DaoFactory() {
        this.personDao = new PersonStorage();
        this.locationDao = new LocationStorage();
        this.personLocationDao = new PersonLocationStorage(this.locationDao);
        this.universityDao = new UniversityStorage();
        this.moduleDao = new ModuleStorage();
    }

    public PersonDao getPersonDao() {
        return this.personDao;
    }

    public LocationDao getLocationDao() {
        return this.locationDao;
    }

    public PersonLocationDao getPersonLocationDao() {
        return this.personLocationDao;
    }

    public UniversityDao getUniversityDao() {
        return this.universityDao;
    }

    public ModuleDao getModuleDao() {
        return this.moduleDao;
    }
}
