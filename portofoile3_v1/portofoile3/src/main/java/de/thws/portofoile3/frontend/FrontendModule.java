package de.thws.portofoile3.frontend;

import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;

import de.thws.portofoile3.model.University;
import de.thws.portofoile3.model.CourseModule;

@Component
public class FrontendModule {
    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8080/api/v1/";

    public FrontendModule(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    // CourseModule Operations
    public CourseModule createCourseModule(CourseModule courseModule) {
        return restTemplate.postForObject(baseUrl + "coursemodule", courseModule, CourseModule.class);
    }

    public CourseModule getCourseModule(Long id) {
        return restTemplate.getForObject(baseUrl + "coursemodule/" + id, CourseModule.class);
    }

    public PagedModel<EntityModel<CourseModule>> getAllCourseModules(int page, int size) {
        ResponseEntity<PagedModel<EntityModel<CourseModule>>> response = restTemplate.getForEntity(
                baseUrl + "coursemodule?page=" + page + "&size=" + size, (Class<PagedModel<EntityModel<CourseModule>>>) (Class<?>) PagedModel.class);
        return response.getBody();
    }

    public void updateCourseModule(Long id, CourseModule courseModule) {
        restTemplate.put(baseUrl + "coursemodule/" + id, courseModule);
    }

    public void deleteCourseModule(Long id) {
        restTemplate.delete(baseUrl + "coursemodule/" + id);
    }
}
