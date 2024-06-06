package de.fhws.fiw.fds.suttondemo.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.fhws.fiw.fds.sutton.client.converters.ClientLinkJsonConverter;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;

@XmlRootElement
public class UniversityClientModel extends AbstractClientModel {

    private String universityName;
    private String country;
    private String departmentName;
    private String departmentUrl;
    private String contactPerson;
    private int incomingStudents;
    private int outgoingStudents;
    private LocalDate nextSpringSemesterStart;
    private LocalDate nextAutumnSemesterStart;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link selfLink;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link modulesLink;

    public UniversityClientModel() {
    }

    public UniversityClientModel(final String universityName, final String country, final String departmentName,
                                 final String departmentUrl, final String contactPerson, final int incomingStudents,
                                 final int outgoingStudents, final LocalDate nextSpringSemesterStart,
                                 final LocalDate nextAutumnSemesterStart) {
        this.universityName = universityName;
        this.country = country;
        this.departmentName = departmentName;
        this.departmentUrl = departmentUrl;
        this.contactPerson = contactPerson;
        this.incomingStudents = incomingStudents;
        this.outgoingStudents = outgoingStudents;
        this.nextSpringSemesterStart = nextSpringSemesterStart;
        this.nextAutumnSemesterStart = nextAutumnSemesterStart;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(final String universityName) {
        this.universityName = universityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(final String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentUrl() {
        return departmentUrl;
    }

    public void setDepartmentUrl(final String departmentUrl) {
        this.departmentUrl = departmentUrl;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(final String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getIncomingStudents() {
        return incomingStudents;
    }

    public void setIncomingStudents(final int incomingStudents) {
        this.incomingStudents = incomingStudents;
    }

    public int getOutgoingStudents() {
        return outgoingStudents;
    }

    public void setOutgoingStudents(final int outgoingStudents) {
        this.outgoingStudents = outgoingStudents;
    }

    public LocalDate getNextSpringSemesterStart() {
        return nextSpringSemesterStart;
    }

    public void setNextSpringSemesterStart(final LocalDate nextSpringSemesterStart) {
        this.nextSpringSemesterStart = nextSpringSemesterStart;
    }

    public LocalDate getNextAutumnSemesterStart() {
        return nextAutumnSemesterStart;
    }

    public void setNextAutumnSemesterStart(final LocalDate nextAutumnSemesterStart) {
        this.nextAutumnSemesterStart = nextAutumnSemesterStart;
    }

    @JsonIgnore
    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    @JsonIgnore
    public Link getModulesLink() {
        return modulesLink;
    }

    public void setModulesLink(Link modulesLink) {
        this.modulesLink = modulesLink;
    }

    @Override
    public String toString() {
        return "University{" + "id=" + id + ", universityName='" + universityName + '\'' + ", country='"
                + country + '\'' + ", departmentName='" + departmentName + '\'' + ", departmentUrl='"
                + departmentUrl + '\'' + ", contactPerson='" + contactPerson + '\'' + ", incomingStudents="
                + incomingStudents + ", outgoingStudents=" + outgoingStudents + ", nextSpringSemesterStart="
                + nextSpringSemesterStart + ", nextAutumnSemesterStart=" + nextAutumnSemesterStart + '}';
    }


}