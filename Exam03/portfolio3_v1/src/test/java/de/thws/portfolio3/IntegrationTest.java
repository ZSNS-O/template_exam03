package de.thws.portfolio3;

import de.thws.portfolio3.frontend.FrontendClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    private static final FrontendClient client = new FrontendClient();
    private static Long universityId;

    @BeforeAll
    public static void setup() throws Exception {
        String universityJson = "{\"name\":\"Test University\"," +
                "\"country\":\"Country\"" +
                ",\"departmentName\":\"Dept\"," +
                "\"departmentUrl\":\"http://dept.url\"," +
                "\"contactPerson\":\"Person\"" +
                ",\"studentsWeCanSend\":5,\"studentsWeCanAccept\":5," +
                "\"nextSpringSemesterStart\":\"2024-01-15\"," +
                "\"nextAutumnSemesterStart\":\"2024-09-01\"}";
        String createResponse = client.post("/university", universityJson);
        System.out.println("Create University Response: " + createResponse); // Logging create response

        // Extract the university ID from the response (simplified, real code should parse the JSON)
        universityId = extractIdFromResponse(createResponse);
        assertTrue(universityId != null && universityId > 0, "Failed to create a university for testing");
    }

    @Test
    public void testCreateAndRetrieveUniversity() throws Exception {
        String getAllResponse = client.get("/university");
        System.out.println("Get All Response: " + getAllResponse); // Logging get all response
        assertTrue(getAllResponse.contains("Test University"));
    }

    @Test
    public void testCreateAndRetrieveCourseModule() throws Exception {
        String courseModuleJson = "{\"moduleName\":\"Test Module\",\"semester\":1,\"creditPoints\":3}";
        String createCourseModuleResponse = client.post("/university/" + universityId + "/coursemodule", courseModuleJson);
        System.out.println("Create Course Module Response: " + createCourseModuleResponse); // Logging create course module response
        assertTrue(createCourseModuleResponse.contains("Test Module"));

        String getAllModulesResponse = client.get("/university/" + universityId + "/coursemodule");
        System.out.println("Get All Modules Response: " + getAllModulesResponse); // Logging get all modules response
        assertTrue(getAllModulesResponse.contains("Test Module"));
    }

    @Test
    public void testUpdateAndDeleteUniversity() throws Exception {
        String updatedUniversityJson = "{\"name\":\"Updated University\"," +
                "\"country\":\"New Country\"," +
                "\"departmentName\":\"New Dept\"" +
                ",\"departmentUrl\":\"http://newdept.url\"," +
                "\"contactPerson\":\"New Person\"" +
                ",\"studentsWeCanSend\":10," +
                "\"studentsWeCanAccept\":10," +
                "\"nextSpringSemesterStart\":\"2025-01-15\"" +
                ",\"nextAutumnSemesterStart\":\"2025-09-01\"}";
        String updateResponse = client.put("/university/" + universityId, updatedUniversityJson);
        System.out.println("Update Response: " + updateResponse); // Logging update response
        assertTrue(updateResponse.contains("Updated University"));

        int deleteResponseCode = client.deleteWithResponseCode("/university/" + universityId);
        System.out.println("Delete Response Code: " + deleteResponseCode); // Logging delete response code
        assertEquals(204, deleteResponseCode);
    }

    private static Long extractIdFromResponse(String response) {
        // Implement logic to extract ID from the response JSON
        // This is a simplified placeholder. In real code, parse the JSON response.
        // Assuming the response contains a JSON object with an "id" field
        String idField = "\"id\":";
        int idIndex = response.indexOf(idField);
        if (idIndex != -1) {
            int startIndex = idIndex + idField.length();
            int endIndex = response.indexOf(",", startIndex);
            if (endIndex == -1) endIndex = response.indexOf("}", startIndex);
            return Long.parseLong(response.substring(startIndex, endIndex).trim());
        }
        return null;
    }
}
