package de.thws.portofoile3;

import de.thws.portofoile3.frontend.Frontend;
import de.thws.portofoile3.model.CourseModule;
import de.thws.portofoile3.model.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.server.ResponseStatusException;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

    @LocalServerPort
    private int port;

    private Frontend frontend;

    @BeforeEach
    public void setUp() {
        frontend = new Frontend(port);
    }

    @Test
    public void testGetAllUniversities() {
        University[] universities = frontend.getAllUniversities();
        assertNotNull(universities);
    }

    @Test
    public void testCreateUniversity() {
        University university = new University();
        university.setName("Test University");
        University createdUniversity = frontend.createUniversity(university);
        assertNotNull(createdUniversity);
        assertEquals("Test University", createdUniversity.getName());
    }

    @Test
    public void testUpdateUniversity() {
        University university = new University();
        university.setName("Initial University");
        University createdUniversity = frontend.createUniversity(university);

        createdUniversity.setName("Updated University");
        University updatedUniversity = frontend.updateUniversity(createdUniversity.getId(), createdUniversity);
        assertEquals("Updated University", updatedUniversity.getName());
    }

    @Test
    public void testDeleteUniversity() {
        // Step 1: Create University
        University university = new University();
        university.setName("To be deleted");
        University createdUniversity = frontend.createUniversity(university);
        assertNotNull(createdUniversity.getId());

        // Step 2: Create CourseModule
        CourseModule courseModule = new CourseModule();
        courseModule.setModuleName("Course to be deleted");
        courseModule.setSemester(1);
        courseModule.setCreditPoints(3);
        CourseModule createdCourseModule = frontend.createCourseModule(createdUniversity.getId(), courseModule);
        assertNotNull(createdCourseModule.getId());

        // Step 3: Verify CourseModule is linked to University
        CourseModule[] courseModulesBeforeDelete = frontend.getAllCourseModules(createdUniversity.getId());
        assertEquals(1, courseModulesBeforeDelete.length);

        // Step 4: Delete University
        frontend.deleteUniversity(createdUniversity.getId());

        // Step 5: Verify University is deleted
        assertThrows(ResponseStatusException.class, () -> frontend.getUniversityById(createdUniversity.getId()));

        // Step 6: Verify CourseModules are also deleted
        assertThrows(ResponseStatusException.class, () -> frontend.getAllCourseModules(createdUniversity.getId()));
    }

    @Test
    public void testGetAllCourseModules() {
        University university = new University();
        university.setName("Test University");
        University createdUniversity = frontend.createUniversity(university);

        CourseModule[] courseModules = frontend.getAllCourseModules(createdUniversity.getId());
        assertNotNull(courseModules);
    }

    @Test
    public void testCreateCourseModule() {
        University university = new University();
        university.setName("Test University");
        University createdUniversity = frontend.createUniversity(university);

        CourseModule courseModule = new CourseModule();
        courseModule.setModuleName("Test Course Module");
        courseModule.setSemester(1); // Ensure semester is either 1 or 2
        courseModule.setCreditPoints(3); // Valid credit points
        CourseModule createdCourseModule = frontend.createCourseModule(createdUniversity.getId(), courseModule);
        assertNotNull(createdCourseModule);
        assertEquals("Test Course Module", createdCourseModule.getModuleName());
    }

    @Test
    public void testUpdateCourseModule() {
        University university = new University();
        university.setName("Test University");
        University createdUniversity = frontend.createUniversity(university);

        CourseModule courseModule = new CourseModule();
        courseModule.setModuleName("Initial Module");
        courseModule.setSemester(1); // Ensure semester is either 1 or 2
        courseModule.setCreditPoints(3); // Valid credit points
        CourseModule createdCourseModule = frontend.createCourseModule(createdUniversity.getId(), courseModule);

        createdCourseModule.setModuleName("Updated Module");
        CourseModule updatedCourseModule = frontend.updateCourseModule(createdUniversity.getId(), createdCourseModule.getId(), createdCourseModule);
        assertEquals("Updated Module", updatedCourseModule.getModuleName());
    }


    }

