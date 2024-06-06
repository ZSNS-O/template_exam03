// Copyright 2022 Peter Braun
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package de.fhws.fiw.fds.suttondemo.server.api.queries;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemo.server.api.models.Person;
import de.fhws.fiw.fds.suttondemo.server.database.DaoFactory;

public class QueryByFirstAndLastName<R> extends AbstractQuery<R, Person> {

    private String firstName;
    private String lastName;

    public QueryByFirstAndLastName(String firstName, String lastName, int offset, int size) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pagingBehavior = new PagingBehaviorUsingOffsetSize<>(offset, size);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    protected CollectionModelResult<Person> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        return DaoFactory.getInstance().getPersonDao().readByFirstNameAndLastName(
                this.firstName,
                this.lastName,
                searchParameter);
    }

}
