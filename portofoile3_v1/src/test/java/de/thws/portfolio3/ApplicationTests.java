package de.thws.portfolio3;

import de.thws.portfolio3.frontend.Frontend;
import de.thws.portfolio3.model.CourseModule;
import de.thws.portfolio3.model.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

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
    public void testGetAllCourseModules() {
        University university = new University();
        university.setName("Test University");
        University createdUniversity = frontend.createUniversity(university);

        CourseModule[] courseModules = frontend.getAllCourseModules(createdUniversity.getId());
        assertNotNull(courseModules);
    }




    }

