package de.thws.portofoile3;

import de.thws.portofoile3.front.UniversityClient;
import de.thws.portofoile3.model.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UniversityClientTests {
    private UniversityClient universityClient;

    @BeforeEach
    public void setUp() {
        this.universityClient = new UniversityClient("http://localhost:8080"); // Assuming the dispatcher URL is localhost
    }

    @Test
    public void testCreateAndGetUniversity() {
        University newUniversity = new University();
        newUniversity.setUniversityName("Test University");
        newUniversity.setCountry("Test Country");
        newUniversity.setDepartmentName("Test Department");
        newUniversity.setDepartmentUrl("http://test.university/department");
        newUniversity.setContactPerson("Test Person");
        newUniversity.setStudentsWeCanSend(10);
        newUniversity.setStudentsWeCanAccept(5);


        University createdUniversity = universityClient.createUniversity(newUniversity);
        assertNotNull(createdUniversity);
        assertNotNull(createdUniversity.getId());

        University fetchedUniversity = universityClient.getUniversityById(createdUniversity.getId());
        assertNotNull(fetchedUniversity);
        assertEquals("Test University", fetchedUniversity.getUniversityName());
    }

    @Test
    public void testUpdateUniversity() {
        University newUniversity = new University();
        newUniversity.setUniversityName("Test University");
        newUniversity.setCountry("Test Country");
        newUniversity.setDepartmentName("Test Department");
        newUniversity.setDepartmentUrl("http://test.university/department");
        newUniversity.setContactPerson("Test Person");
        newUniversity.setStudentsWeCanSend(10);
        newUniversity.setStudentsWeCanAccept(5);


        University createdUniversity = universityClient.createUniversity(newUniversity);
        assertNotNull(createdUniversity);
        assertNotNull(createdUniversity.getId());

        createdUniversity.setUniversityName("Updated Test University");
        University updatedUniversity = universityClient.updateUniversity(createdUniversity.getId(), createdUniversity);
        assertNotNull(updatedUniversity);
        assertEquals("Updated Test University", updatedUniversity.getUniversityName());
    }

    @Test
    public void testDeleteUniversity() {
        University newUniversity = new University();
        newUniversity.setUniversityName("Test University");
        newUniversity.setCountry("Test Country");
        newUniversity.setDepartmentName("Test Department");
        newUniversity.setDepartmentUrl("http://test.university/department");
        newUniversity.setContactPerson("Test Person");
        newUniversity.setStudentsWeCanSend(10);
        newUniversity.setStudentsWeCanAccept(5);


        University createdUniversity = universityClient.createUniversity(newUniversity);
        assertNotNull(createdUniversity);
        assertNotNull(createdUniversity.getId());

        universityClient.deleteUniversity(createdUniversity.getId());

        University fetchedUniversity = universityClient.getUniversityById(createdUniversity.getId());
        assertNull(fetchedUniversity);
    }

}
