package de.thws.portofoile3.frontend;

import de.thws.portofoile3.model.University;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class UniversityFrontend {
    // University Operations
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/v1/";

    public UniversityFrontend(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public University getUniversity(Long id) {
        return restTemplate.getForObject(baseUrl + "university/" + id, University.class);
    }

    public PagedModel<EntityModel<University>> getAllUniversities(int page, int size) {
        ResponseEntity<PagedModel<EntityModel<University>>> response = restTemplate.getForEntity(
                baseUrl + "university?page=" + page + "&size=" + size, (Class<PagedModel<EntityModel<University>>>) (Class<?>) PagedModel.class);
        return response.getBody();
    }

    public void updateUniversity(Long id, University university) {
        restTemplate.put(baseUrl + "university/" + id, university);
    }

    public void deleteUniversity(Long id) {
        restTemplate.delete(baseUrl + "university/" + id);
    }
}
