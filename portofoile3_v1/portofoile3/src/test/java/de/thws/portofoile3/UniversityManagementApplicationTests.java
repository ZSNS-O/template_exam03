package de.thws.portofoile3;

import de.thws.portofoile3.model.University;
import de.thws.portofoile3.frontend.UniversityFrontend;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.PagedModel;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class UniversityManagementApplicationTests {

    private final UniversityFrontend universityFrontend = new UniversityFrontend();

    @Test
    public void testGetAllUniversities() {
        List<University> universities = universityFrontend.getAllUniversities();
        Assert.notEmpty(universities, "The list of universities should not be empty");
    }

    @Test
    public void testSaveAndGetUniversity() {
        University university = new University();
        university.setUniversityName("Test University");
        university.setCountry("Test Country");
        university.setDepartmentName("Test Department");
        university.setDepartmentUrl("http://test.url");
        university.setContactPerson("Test Contact");
        university.setStudentsWeCanSend(5);
        university.setStudentsWeCanAccept(5);
        university.setNextSpringSemesterStart(LocalDate.parse("2025-01-25"));
        university.setNextAutumnSemesterStart(LocalDate.parse("2024-09-01"));

        University savedUniversity = universityFrontend.saveUniversity(university);
        University retrievedUniversity = universityFrontend.getUniversityById(savedUniversity.getId());

        Assert.notNull(retrievedUniversity, "The university should not be null");
        Assert.isTrue(savedUniversity.getUniversityName().equals(retrievedUniversity.getUniversityName()), "The university name should match");
    }

    @Test
    public void testDeleteUniversity() {
        University university = new University();
        university.setUniversityName("Test University");
        university.setCountry("Test Country");
        university.setDepartmentName("Test Department");
        university.setDepartmentUrl("http://test.url");
        university.setContactPerson("Test Contact");
        university.setStudentsWeCanSend(5);
        university.setStudentsWeCanAccept(5);
        university.setNextSpringSemesterStart(LocalDate.parse("2025-01-25"));
        university.setNextAutumnSemesterStart(LocalDate.parse("2024-09-01"));

        University savedUniversity = universityFrontend.saveUniversity(university);
        universityFrontend.deleteUniversity(savedUniversity.getId());

        University deletedUniversity = universityFrontend.getUniversityById(savedUniversity.getId());
        Assert.isNull(deletedUniversity, "The university should be null after deletion");
    }

    @Test
    public void testSearchUniversities() {
        PagedModel<EntityModel<University>> universities = universityFrontend.searchUniversities(
                "Test University", "Test Country", null, 0, 10, "name", "asc");
        Assert.notEmpty(universities.getContent(), "The list of universities should not be empty");
    }
}
