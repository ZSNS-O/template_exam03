package de.thws.portfolio3;

import de.thws.portfolio3.frontend.Frontend3;
import de.thws.portfolio3.model.CourseModule;
import de.thws.portfolio3.model.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    private Frontend3 frontend;
    private static final URI DISPATCHER_URL = URI.create("http://localhost:8080");

    @BeforeEach
    public void setup() {
        frontend = new Frontend3(DISPATCHER_URL);
    }

    @Test
    public void testCreateAndGetUniversity() {
        University university = createSampleUniversity();

        Optional<University> createdUniversity = frontend.createUniversity(university);
        assertTrue(createdUniversity.isPresent(), "University creation failed");

        Optional<University> fetchedUniversity = frontend.getUniversityById(createdUniversity.get().getId());
        assertTrue(fetchedUniversity.isPresent(), "University fetch by ID failed");
        assertEquals("Test University", fetchedUniversity.get().getName());
    }

    @Test
    public void testCreateAndGetCourseModule() {
        University university = createSampleUniversity();
        Optional<University> createdUniversity = frontend.createUniversity(university);
        assertTrue(createdUniversity.isPresent(), "University creation failed");

        CourseModule courseModule = createSampleCourseModule();

        Optional<CourseModule> createdCourseModule = frontend.createCourseModule(createdUniversity.get().getId(), courseModule);
        assertTrue(createdCourseModule.isPresent(), "CourseModule creation failed");

        Optional<CourseModule> fetchedCourseModule = frontend.getCourseModuleById(createdUniversity.get().getId(), createdCourseModule.get().getId());
        assertTrue(fetchedCourseModule.isPresent(), "CourseModule fetch by ID failed");
        assertEquals("Test CourseModule", fetchedCourseModule.get().getModuleName());
    }

    @Test
    public void testUpdateUniversity() {
        University university = createSampleUniversity();
        Optional<University> createdUniversity = frontend.createUniversity(university);
        assertTrue(createdUniversity.isPresent(), "University creation failed");

        createdUniversity.get().setName("Updated University");

        Optional<University> updatedUniversity = frontend.updateUniversity(createdUniversity.get().getId(), createdUniversity.get());
        assertTrue(updatedUniversity.isPresent(), "University update failed");
        assertEquals("Updated University", updatedUniversity.get().getName());
    }

    @Test
    public void testDeleteUniversity() {
        University university = createSampleUniversity();
        Optional<University> createdUniversity = frontend.createUniversity(university);
        assertTrue(createdUniversity.isPresent(), "University creation failed");

        boolean isDeleted = frontend.deleteUniversity(createdUniversity.get().getId());
        assertTrue(isDeleted, "University deletion failed");

        Optional<University> fetchedUniversity = frontend.getUniversityById(createdUniversity.get().getId());
        assertFalse(fetchedUniversity.isPresent(), "Deleted University still exists");
    }

    @Test
    public void testUpdateCourseModule() {
        University university = createSampleUniversity();
        Optional<University> createdUniversity = frontend.createUniversity(university);
        assertTrue(createdUniversity.isPresent(), "University creation failed");

        CourseModule courseModule = createSampleCourseModule();
        Optional<CourseModule> createdCourseModule = frontend.createCourseModule(createdUniversity.get().getId(), courseModule);
        assertTrue(createdCourseModule.isPresent(), "CourseModule creation failed");

        createdCourseModule.get().setModuleName("Updated CourseModule");

        Optional<CourseModule> updatedCourseModule = frontend.updateCourseModule(createdUniversity.get().getId(), createdCourseModule.get().getId(), createdCourseModule.get());
        assertTrue(updatedCourseModule.isPresent(), "CourseModule update failed");
        assertEquals("Updated CourseModule", updatedCourseModule.get().getModuleName());
    }

    @Test
    public void testDeleteCourseModule() {
        University university = createSampleUniversity();
        Optional<University> createdUniversity = frontend.createUniversity(university);
        assertTrue(createdUniversity.isPresent(), "University creation failed");

        CourseModule courseModule = createSampleCourseModule();
        Optional<CourseModule> createdCourseModule = frontend.createCourseModule(createdUniversity.get().getId(), courseModule);
        assertTrue(createdCourseModule.isPresent(), "CourseModule creation failed");

        boolean isDeleted = frontend.deleteCourseModule(createdUniversity.get().getId(), createdCourseModule.get().getId());
        assertTrue(isDeleted, "CourseModule deletion failed");

        Optional<CourseModule> fetchedCourseModule = frontend.getCourseModuleById(createdUniversity.get().getId(), createdCourseModule.get().getId());
        assertFalse(fetchedCourseModule.isPresent(), "Deleted CourseModule still exists");
    }


    private University createSampleUniversity() {
        University university = new University();
        university.setName("Test University");
        university.setCountry("Test Country");
        university.setDepartmentName("Test Department");
        university.setDepartmentUrl("http://testurl.com");
        university.setContactPerson("Test Contact");
        university.setStudentsWeCanSend(10);
        university.setStudentsWeCanAccept(10);
        university.setNextSpringSemesterStart(LocalDate.now());
        university.setNextAutumnSemesterStart(LocalDate.now());
        return university;
    }

    private CourseModule createSampleCourseModule() {
        CourseModule courseModule = new CourseModule();
        courseModule.setModuleName("Test CourseModule");
        courseModule.setSemester(1);
        courseModule.setCreditPoints(5);
        return courseModule;
    }
}
