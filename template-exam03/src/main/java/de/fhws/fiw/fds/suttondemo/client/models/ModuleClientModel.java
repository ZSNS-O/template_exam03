package de.fhws.fiw.fds.suttondemo.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.fhws.fiw.fds.sutton.client.converters.ClientLinkJsonConverter;
import de.fhws.fiw.fds.sutton.client.model.AbstractClientModel;
import de.fhws.fiw.fds.sutton.client.utils.Link;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModuleClientModel extends AbstractClientModel {

    private String moduleName;
    private int semesterOffered;
    private int creditPoints;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link selfLink;

    @JsonDeserialize(using = ClientLinkJsonConverter.class)
    private Link universityLink;

    public ModuleClientModel() {
    }

    public ModuleClientModel(final String moduleName, final int semesterOffered, final int creditPoints) {
        this.moduleName = moduleName;
        this.semesterOffered = semesterOffered;
        this.creditPoints = creditPoints;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(final String moduleName) {
        this.moduleName = moduleName;
    }

    public int getSemesterOffered() {
        return semesterOffered;
    }

    public void setSemesterOffered(final int semesterOffered) {
        this.semesterOffered = semesterOffered;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public void setCreditPoints(final int creditPoints) {
        this.creditPoints = creditPoints;
    }

    @JsonIgnore
    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    @JsonIgnore
    public Link getUniversityLink() {
        return universityLink;
    }

    public void setUniversityLink(Link universityLink) {
        this.universityLink = universityLink;
    }

    @Override
    public String toString() {
        return "Module{" + "id=" + id + ", moduleName='" + moduleName + '\'' + ", semesterOffered="
                + semesterOffered + ", creditPoints=" + creditPoints + '}';
    }
}