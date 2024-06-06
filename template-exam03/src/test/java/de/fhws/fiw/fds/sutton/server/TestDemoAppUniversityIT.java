package de.fhws.fiw.fds.sutton.server;

import com.github.javafaker.Faker;
import de.fhws.fiw.fds.suttondemo.client.models.UniversityClientModel;
import de.fhws.fiw.fds.suttondemo.client.rest.DemoRestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDemoAppUniversityIT {
    final private Faker faker = new Faker();
    private DemoRestClient client;

    @BeforeEach
    public void setUp() throws IOException {
        this.client = new DemoRestClient();
        this.client.resetDatabase();
    }

    @Test
    public void test_dispatcher_is_available() throws IOException {
        client.start();
        assertEquals(200, client.getLastStatusCode());
    }

    @Test
    public void test_dispatcher_is_get_all_universities_allowed() throws IOException {
        client.start();
        assertTrue(client.isGetAllUniversitiesAllowed());
    }

    @Test
    public void test_create_university_is_create_university_allowed() throws IOException {
        client.start();
        assertTrue(client.isCreateUniversityAllowed());
    }

    @Test
    public void test_create_university() throws IOException {
        client.start();

        var university = new UniversityClientModel();
        university.setUniversityName("FHWS");
        university.setCountry("Germany");

        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());
    }

    @Test
    public void test_create_university_and_get_new_university() throws IOException {
        client.start();

        var university = new UniversityClientModel();
        university.setUniversityName("FHWS");
        university.setCountry("Germany");

        client.createUniversity(university);
        assertEquals(201, client.getLastStatusCode());
        assertTrue(client.isGetSingleUniversityAllowed());

        client.getSingleUniversity();
        assertEquals(200, client.getLastStatusCode());

        var universityFromServer = client.universityData().getFirst();
        assertEquals("FHWS", universityFromServer.getUniversityName());
    }

    @Test
    public void test_create_5_universities_and_get_all() throws IOException {
        for (int i = 0; i < 5; i++) {
            client.start();

            var university = new UniversityClientModel();
            university.setUniversityName(faker.university().name());
            university.setCountry(faker.country().name());

            client.createUniversity(university);
            assertEquals(201, client.getLastStatusCode());
        }

        client.start();
        assertTrue(client.isGetAllUniversitiesAllowed());

        client.getAllUniversities();
        assertEquals(200, client.getLastStatusCode());
        assertEquals(5, client.universityData().size());

        client.setUniversityCursor(0);
        client.getSingleUniversity();
        assertEquals(200, client.getLastStatusCode());
    }
}
