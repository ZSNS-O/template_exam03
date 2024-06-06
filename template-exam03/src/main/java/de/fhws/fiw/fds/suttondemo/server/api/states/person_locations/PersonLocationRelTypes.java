package de.fhws.fiw.fds.suttondemo.server.api.states.person_locations;

public interface PersonLocationRelTypes {
	String CREATE_LOCATION = "createLocationOfPerson";
	String GET_ALL_LINKED_LOCATIONS = "getAllLocationsOfPerson";
	String GET_ALL_LOCATIONS = "getAllLinkableLocations";
	String UPDATE_SINGLE_LOCATION = "updateLocationOfPerson";
	String CREATE_LINK_FROM_PERSON_TO_LOCATION = "linkPersonToLocation";
	String DELETE_LINK_FROM_PERSON_TO_LOCATION = "unlinkPersonToLocation";
	String GET_SINGLE_LOCATION = "getLocationOfPerson";

}
