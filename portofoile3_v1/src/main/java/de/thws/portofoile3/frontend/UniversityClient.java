package de.thws.portofoile3.frontend;
import de.thws.portofoile3.model.University;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UniversityClient {

    private final RestTemplate restTemplate;
    private final String dispatcherUrl;

    public UniversityClient(String dispatcherUrl) {
        this.restTemplate = new RestTemplate();
        this.dispatcherUrl = dispatcherUrl;
    }

    public University getUniversityById(Long id) {
        String url = dispatcherUrl + "/university/" + id;
        ResponseEntity<University> response = restTemplate.getForEntity(url, University.class);
        return response.getBody();
    }

    public University createUniversity(University university) {
        String url = dispatcherUrl + "/university";
        ResponseEntity<University> response = restTemplate.postForEntity(url, university, University.class);
        return response.getBody();
    }

    public University updateUniversity(Long id, University universityDetails) {
        String url = dispatcherUrl + "/university/" + id;
        restTemplate.put(url, universityDetails);
        return getUniversityById(id);
    }

    public void deleteUniversity(Long id) {
        String url = dispatcherUrl + "/university/" + id;
        restTemplate.delete(url);
    }


}
