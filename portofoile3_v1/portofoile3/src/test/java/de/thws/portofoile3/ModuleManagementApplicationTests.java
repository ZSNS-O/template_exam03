package de.thws.portofoile3;

import de.thws.portofoile3.frontend.FrontendModule;
import de.thws.portofoile3.model.University;
import de.thws.portofoile3.model.CourseModule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@SpringBootTest
public class IntegrationTests {

    @Autowired
    private FrontendModule frontend;

    @Test
    public void testCreateAndGetUniversity() {
        University university = new University(null, "Test University", "Test Country", "Test Department", "http://test.com", "Test Person", 5, 5, LocalDate.of(2025, 1, 25), LocalDate.of(2024, 9, 1), null);
        University createdUniversity = frontend.createUniversity(university);

        assertNotNull(createdUniversity.getId());

        String universityUrl = frontend.getLinkHref(EntityModel.of(createdUniversity), "self");
        University retrievedUniversity = frontend.getUniversity(universityUrl);

        assertEquals("Test University", retrievedUniversity.getName());
    }

    @Test
    public void testCreateAndGetCourseModule() {
        University university = new University(null, "Test University", "Test Country", "Test Department", "http://test.com", "Test Person", 5, 5, LocalDate.of(2025, 1, 25), LocalDate.of(2024, 9, 1), null);
        University createdUniversity = frontend.createUniversity(university);

        CourseModule courseModule = new CourseModule(null, "Quantum Computing", 1, 5, createdUniversity);
        CourseModule createdModule = frontend.createCourseModule(courseModule);

        assertNotNull(createdModule.getId());

        String moduleUrl = frontend.getLinkHref(EntityModel.of(createdModule), "self");
        CourseModule retrievedModule = frontend.getCourseModule(moduleUrl);

        assertEquals("Quantum Computing", retrievedModule.getModuleName());
    }

    @Test
    public void testPagingAndSortingUniversities() {
        PagedModel<EntityModel<University>> universitiesPage1 = frontend.getAllUniversities(0, 10);
        PagedModel<EntityModel<University>> universitiesPage2 = frontend.getAllUniversities(1, 10);

        assertNotNull(universitiesPage1);
        assertNotNull(universitiesPage2);
        assertTrue(universitiesPage1.getMetadata().getTotalElements() > 0);

        String nextPageUrl = frontend.getPagedLinkHref(universitiesPage1, "next");
        PagedModel<EntityModel<University>> nextPage = frontend.getAllUniversities(nextPageUrl);

        assertNotNull(nextPage);
    }
}
