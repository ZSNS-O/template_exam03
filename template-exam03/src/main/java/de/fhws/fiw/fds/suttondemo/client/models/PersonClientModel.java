// Copyright 2022 Peter Braun
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package de.fhws.fiw.fds.suttondemo.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.fhws.fiw.fds.sutton.client.converters.ClientLinkJsonConverter;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;

@XmlRootElement
public class PersonClientModel extends AbstractClientModel {

    private String firstName;
    private String lastName;

    private LocalDate birthDate = LocalDate.of(1970, 1, 1);
    private String emailAddress;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link selfLink;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link location;

    public PersonClientModel() {
    }

    public PersonClientModel(final String firstname, final String lastname,
                             final String emailAddress, final LocalDate birthdate) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.birthDate = birthdate;
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @JsonIgnore
    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    @JsonIgnore
    public Link getLocation() {
        return location;
    }

    public void setLocation(Link location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='"
                + lastName + '\'' + ", birthDate=" + birthDate + ", emailAddress='" + emailAddress
                + '\'' + '}';
    }

}
