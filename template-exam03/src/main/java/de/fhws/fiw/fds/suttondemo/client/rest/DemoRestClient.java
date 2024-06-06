package de.fhws.fiw.fds.suttondemo.client.rest;

import de.fhws.fiw.fds.sutton.client.rest2.AbstractRestClient;
import de.fhws.fiw.fds.suttondemo.client.models.LocationClientModel;
import de.fhws.fiw.fds.suttondemo.client.models.PersonClientModel;
import de.fhws.fiw.fds.suttondemo.client.models.UniversityClientModel;
import de.fhws.fiw.fds.suttondemo.client.models.ModuleClientModel;
import de.fhws.fiw.fds.suttondemo.client.web.PersonWebClient;
import de.fhws.fiw.fds.suttondemo.client.web.UniversityWebClient;
import de.fhws.fiw.fds.suttondemo.client.web.ModuleWebClient;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DemoRestClient extends AbstractRestClient {
    private static final String BASE_URL = "http://localhost:8080/demo/api";
    private static final String GET_ALL_PERSONS = "getAllPersons";
    private static final String CREATE_PERSON = "createPerson";
    private static final String GET_ALL_UNIVERSITIES = "getAllUniversities";
    private static final String CREATE_UNIVERSITY = "createUniversity";
    private static final String GET_ALL_MODULES = "getAllModules";
    private static final String CREATE_MODULE = "createModule";

    private List<PersonClientModel> currentPersonData;
    private int cursorPersonData = 0;

    private List<UniversityClientModel> currentUniversityData;
    private int cursorUniversityData = 0;

    private List<ModuleClientModel> currentModuleData;
    private int cursorModuleData = 0;

    private List<LocationClientModel> currentLocationData;
    private int cursorLocationData = 0;

    final private PersonWebClient personClient;
    final private UniversityWebClient universityClient;
    final private ModuleWebClient moduleClient;

    public DemoRestClient() {
        super();
        this.personClient = new PersonWebClient();
        this.universityClient = new UniversityWebClient();
        this.moduleClient = new ModuleWebClient();
        this.currentPersonData = Collections.emptyList();
        this.currentUniversityData = Collections.emptyList();
        this.currentModuleData = Collections.emptyList();
    }

    public void resetDatabase() throws IOException {
        processResponse(this.personClient.resetDatabaseOnServer(BASE_URL), (response) -> {});
        processResponse(this.universityClient.resetDatabaseOnServer(BASE_URL), (response) -> {});
        processResponse(this.moduleClient.resetDatabaseOnServer(BASE_URL), (response) -> {});
    }

    public void start() throws IOException {
        processResponse(this.personClient.getDispatcher(BASE_URL), (response) -> {});
        processResponse(this.universityClient.getDispatcher(BASE_URL), (response) -> {});
        processResponse(this.moduleClient.getDispatcher(BASE_URL), (response) -> {});
    }

    // Person methods

    public boolean isCreatePersonAllowed() {
        return isLinkAvailable(CREATE_PERSON);
    }

    public void createPerson(PersonClientModel person) throws IOException {
        if (isCreatePersonAllowed()) {
            processResponse(this.personClient.postNewPerson(getUrl(CREATE_PERSON), person), (response) -> {
                this.currentPersonData = Collections.emptyList();
                this.cursorPersonData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isGetAllPersonsAllowed() {
        return isLinkAvailable(GET_ALL_PERSONS);
    }

    public void getAllPersons() throws IOException {
        if (isGetAllPersonsAllowed()) {
            processResponse(this.personClient.getCollectionOfPersons(getUrl(GET_ALL_PERSONS)), (response) -> {
                this.currentPersonData = new LinkedList<>(response.getResponseData());
                this.cursorPersonData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isGetSinglePersonAllowed() {
        return !this.currentPersonData.isEmpty() || isLocationHeaderAvailable();
    }

    public List<PersonClientModel> personData() {
        if (this.currentPersonData.isEmpty()) {
            throw new IllegalStateException();
        }

        return this.currentPersonData;
    }

    public void setPersonCursor(int index) {
        if (0 <= index && index < this.currentPersonData.size()) {
            this.cursorPersonData = index;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void getSinglePerson() throws IOException {
        if (isLocationHeaderAvailable()) {
            getSinglePerson(getLocationHeaderURL());
        } else if (!this.currentPersonData.isEmpty()) {
            getSinglePerson(this.cursorPersonData);
        } else {
            throw new IllegalStateException();
        }
    }

    public void getSinglePerson(int index) throws IOException {
        getSinglePerson(this.currentPersonData.get(index).getSelfLink().getUrl());
    }

    private void getSinglePerson(String url) throws IOException {
        processResponse(this.personClient.getSinglePerson(url), (response) -> {
            this.currentPersonData = new LinkedList<>(response.getResponseData());
            this.cursorPersonData = 0;
        });
    }

    // University methods

    public boolean isCreateUniversityAllowed() {
        return isLinkAvailable(CREATE_UNIVERSITY);
    }

    public void createUniversity(UniversityClientModel university) throws IOException {
        if (isCreateUniversityAllowed()) {
            processResponse(this.universityClient.postNewUniversity(getUrl(CREATE_UNIVERSITY), university), (response) -> {
                this.currentUniversityData = Collections.emptyList();
                this.cursorUniversityData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isGetAllUniversitiesAllowed() {
        return isLinkAvailable(GET_ALL_UNIVERSITIES);
    }

    public void getAllUniversities() throws IOException {
        if (isGetAllUniversitiesAllowed()) {
            processResponse(this.universityClient.getCollectionOfUniversities(getUrl(GET_ALL_UNIVERSITIES)), (response) -> {
                this.currentUniversityData = new LinkedList<>(response.getResponseData());
                this.cursorUniversityData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isGetSingleUniversityAllowed() {
        return !this.currentUniversityData.isEmpty() || isLocationHeaderAvailable();
    }

    public List<UniversityClientModel> universityData() {
        if (this.currentUniversityData.isEmpty()) {
            throw new IllegalStateException();
        }

        return this.currentUniversityData;
    }

    public void setUniversityCursor(int index) {
        if (0 <= index && index < this.currentUniversityData.size()) {
            this.cursorUniversityData = index;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void getSingleUniversity() throws IOException {
        if (isLocationHeaderAvailable()) {
            getSingleUniversity(getLocationHeaderURL());
        } else if (!this.currentUniversityData.isEmpty()) {
            getSingleUniversity(this.cursorUniversityData);
        } else {
            throw new IllegalStateException();
        }
    }

    public void getSingleUniversity(int index) throws IOException {
        getSingleUniversity(this.currentUniversityData.get(index).getSelfLink().getUrl());
    }

    private void getSingleUniversity(String url) throws IOException {
        processResponse(this.universityClient.getSingleUniversity(url), (response) -> {
            this.currentUniversityData = new LinkedList<>(response.getResponseData());
            this.cursorUniversityData = 0;
        });
    }

    // Module methods

    public boolean isCreateModuleAllowed() {
        return isLinkAvailable(CREATE_MODULE);
    }

    public void createModule(ModuleClientModel module) throws IOException {
        if (isCreateModuleAllowed()) {
            processResponse(this.moduleClient.postNewModule(getUrl(CREATE_MODULE), module), (response) -> {
                this.currentModuleData = Collections.emptyList();
                this.cursorModuleData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isGetAllModulesAllowed() {
        return isLinkAvailable(GET_ALL_MODULES);
    }

    public void getAllModules() throws IOException {
        if (isGetAllModulesAllowed()) {
            processResponse(this.moduleClient.getCollectionOfModules(getUrl(GET_ALL_MODULES)), (response) -> {
                this.currentModuleData = new LinkedList<>(response.getResponseData());
                this.cursorModuleData = 0;
            });
        } else {
            throw new IllegalStateException();
        }
    }

    public boolean isGetSingleModuleAllowed() {
        return !this.currentModuleData.isEmpty() || isLocationHeaderAvailable();
    }

    public List<ModuleClientModel> moduleData() {
        if (this.currentModuleData.isEmpty()) {
            throw new IllegalStateException();
        }

        return this.currentModuleData;
    }

    public void setModuleCursor(int index) {
        if (0 <= index && index < this.currentModuleData.size()) {
            this.cursorModuleData = index;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void getSingleModule() throws IOException {
        if (isLocationHeaderAvailable()) {
            getSingleModule(getLocationHeaderURL());
        } else if (!this.currentModuleData.isEmpty()) {
            getSingleModule(this.cursorModuleData);
        } else {
            throw new IllegalStateException();
        }
    }

    public void getSingleModule(int index) throws IOException {
        getSingleModule(this.currentModuleData.get(index).getSelfLink().getUrl());
    }

    private void getSingleModule(String url) throws IOException {
        processResponse(this.moduleClient.getSingleModule(url), (response) -> {
            this.currentModuleData = new LinkedList<>(response.getResponseData());
            this.cursorModuleData = 0;
        });
    }

    /*
     *  The rest of the class is omitted for brevity
     */
}
