package de.fhws.fiw.fds.suttondemo.server.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Link;
import de.fhws.fiw.fds.sutton.server.api.hyperlinks.annotations.SuttonLink;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.xml.bind.annotation.XmlRootElement;

@JsonRootName("module")
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlRootElement(name = "module")
public class Module extends AbstractModel {

    private String moduleName;
    private int semesterOffered;
    private int creditPoints;

    @SuttonLink(
            value = "module/${id}",
            rel = "self"
    )
    private transient Link selfLink;

    @SuttonLink(
            value = "modules/${id}/university",
            rel = "getUniversityOfModule"
    )
    private transient Link universityLink;

    public Module() {
        // make JPA happy
    }

    public Module(final String moduleName, final int semesterOffered, final int creditPoints) {
        this.moduleName = moduleName;
        this.semesterOffered = semesterOffered;
        this.creditPoints = creditPoints;
    }

    public Link getSelfLink() {
        return selfLink;
    }
    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    public Link getUniversityLink() {
        return universityLink;
    }

    public void setUniversityLink(Link universityLink) {
        this.universityLink = universityLink;
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

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\''
                + ", semesterOffered=" + semesterOffered
                + ", creditPoints=" + creditPoints
                + '}';
    }
}