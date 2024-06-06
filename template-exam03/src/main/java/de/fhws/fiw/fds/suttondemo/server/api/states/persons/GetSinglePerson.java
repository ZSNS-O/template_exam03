/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package de.fhws.fiw.fds.suttondemo.server.api.states.persons;

import de.fhws.fiw.fds.sutton.server.api.caching.CachingUtils;
import de.fhws.fiw.fds.sutton.server.api.caching.EtagGenerator;
import de.fhws.fiw.fds.sutton.server.api.serviceAdapters.responseAdapter.JerseyResponse;
import de.fhws.fiw.fds.sutton.server.api.services.ServiceContext;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.suttondemo.server.api.models.Person;
import de.fhws.fiw.fds.suttondemo.server.api.states.person_locations.PersonLocationRelTypes;
import de.fhws.fiw.fds.suttondemo.server.api.states.person_locations.PersonLocationUri;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;
import jakarta.ws.rs.core.Response;

public class GetSinglePerson extends AbstractGetState<Response, Person> {

    public GetSinglePerson(ServiceContext serviceContext, long requestedId) {
        super(serviceContext, requestedId);
        this.suttonResponse = new JerseyResponse<>();
    }

    @Override
    protected SingleModelResult<Person> loadModel() {
        return DaoFactory.getInstance().getPersonDao().readById(this.requestedId);
    }

    @Override
    protected boolean clientKnowsCurrentModelState(AbstractModel modelFromDatabase) {
        return this.suttonRequest.clientKnowsCurrentModel(modelFromDatabase);
    }

    @Override
    protected void defineHttpCaching() {
        final String eTagOfModel = EtagGenerator.createEtag(this.requestedModel.getResult());
        this.suttonResponse.entityTag(eTagOfModel);
        this.suttonResponse.cacheControl(CachingUtils.create30SecondsPublicCaching());
    }

    @Override
    protected void defineTransitionLinks() {
        addLink( PersonUri.REL_PATH_ID, PersonRelTypes.UPDATE_SINGLE_PERSON, getAcceptRequestHeader( ),
                this.requestedId );
        addLink( PersonUri.REL_PATH_ID, PersonRelTypes.DELETE_SINGLE_PERSON, getAcceptRequestHeader( ),
                this.requestedId );
        addLink( PersonLocationUri.REL_PATH, PersonLocationRelTypes.CREATE_LOCATION, getAcceptRequestHeader( ),
                this.requestedId );
    }
}
