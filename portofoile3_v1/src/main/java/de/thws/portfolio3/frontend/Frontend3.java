package de.thws.portfolio3.frontend;

import de.thws.portfolio3.model.CourseModule;
import de.thws.portfolio3.model.University;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

public class Frontend3 {

    private final RestTemplate restTemplate;
    private final URI dispatcherUrl;

    public Frontend3(URI dispatcherUrl) {
        this.restTemplate = new RestTemplate();
        this.dispatcherUrl = dispatcherUrl;
    }

    public Optional<University> getUniversityById(Long id) {
        try {
            EntityModel<University> entityModel = restTemplate.exchange(
                    dispatcherUrl.resolve("/api/v1/university/" + id),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<EntityModel<University>>() {}
            ).getBody();
            return Optional.of(entityModel.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<CourseModule> getCourseModuleById(Long universityId, Long id) {
        try {
            EntityModel<CourseModule> entityModel = restTemplate.exchange(
                    dispatcherUrl.resolve("/api/v1/university/" + universityId + "/coursemodule/" + id),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<EntityModel<CourseModule>>() {}
            ).getBody();
            return Optional.of(entityModel.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<University> createUniversity(University university) {
        try {
            EntityModel<University> entityModel = restTemplate.exchange(
                    dispatcherUrl.resolve("/api/v1/university"),
                    HttpMethod.POST,
                    new HttpEntity<>(university),
                    new ParameterizedTypeReference<EntityModel<University>>() {}
            ).getBody();
            return Optional.of(entityModel.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<CourseModule> createCourseModule(Long universityId, CourseModule courseModule) {
        try {
            EntityModel<CourseModule> entityModel = restTemplate.exchange(
                    dispatcherUrl.resolve("/api/v1/university/" + universityId + "/coursemodule"),
                    HttpMethod.POST,
                    new HttpEntity<>(courseModule),
                    new ParameterizedTypeReference<EntityModel<CourseModule>>() {}
            ).getBody();
            return Optional.of(entityModel.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean deleteUniversity(Long id) {
        try {
            restTemplate.exchange(
                    dispatcherUrl.resolve("/api/v1/university/" + id),
                    HttpMethod.DELETE,
                    null,
                    Void.class
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCourseModule(Long universityId, Long id) {
        try {
            restTemplate.exchange(
                    dispatcherUrl.resolve("/api/v1/university/" + universityId + "/coursemodule/" + id),
                    HttpMethod.DELETE,
                    null,
                    Void.class
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<University> updateUniversity(Long id, University university) {
        try {
            restTemplate.exchange(
                    dispatcherUrl.resolve("/api/v1/university/" + id),
                    HttpMethod.PUT,
                    new HttpEntity<>(university),
                    Void.class
            );
            return getUniversityById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<CourseModule> updateCourseModule(Long universityId, Long id, CourseModule courseModule) {
        try {
            restTemplate.exchange(
                    dispatcherUrl.resolve("/api/v1/university/" + universityId + "/coursemodule/" + id),
                    HttpMethod.PUT,
                    new HttpEntity<>(courseModule),
                    Void.class
            );
            return getCourseModuleById(universityId, id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public PagedModel<EntityModel<University>> getAllUniversities(int page, int size) {
        try {
            return restTemplate.exchange(
                    dispatcherUrl.resolve("/api/v1/university?page=" + page + "&size=" + size),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<PagedModel<EntityModel<University>>>() {}
            ).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PagedModel<EntityModel<CourseModule>> getAllCourseModules(Long universityId, int page, int size) {
        try {
            ResponseEntity<PagedModel<EntityModel<CourseModule>>> response = restTemplate.exchange(
                    dispatcherUrl.resolve("/api/v1/university/" + universityId + "/coursemodule?page=" + page + "&size=" + size),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<PagedModel<EntityModel<CourseModule>>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
