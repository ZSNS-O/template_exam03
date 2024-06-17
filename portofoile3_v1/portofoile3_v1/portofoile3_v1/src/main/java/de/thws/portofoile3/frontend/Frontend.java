package de.thws.portofoile3.frontend;

import de.thws.portofoile3.model.CourseModule;
import de.thws.portofoile3.model.University;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;

public class Frontend {
    private final String dispatcherUrl;
    private final RestTemplate restTemplate;

    public Frontend(int port) {
        this.dispatcherUrl = "http://localhost:" + port + "/api/v1";
        this.restTemplate = new RestTemplate();
    }

    public University[] getAllUniversities() {
        try {
            return restTemplate.getForObject(new URI(dispatcherUrl + "/university"), University[].class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode().value()), e.getResponseBodyAsString(), e);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            return new University[]{};
        }
    }

    public University getUniversityById(Long id) {
        try {
            return restTemplate.getForObject(new URI(dispatcherUrl + "/university/" + id), University.class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode().value()), e.getResponseBodyAsString(), e);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public University createUniversity(University university) {
        try {
            return restTemplate.postForObject(new URI(dispatcherUrl + "/university"), university, University.class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode().value()), e.getResponseBodyAsString(), e);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public University updateUniversity(Long id, University university) {
        try {
            restTemplate.put(new URI(dispatcherUrl + "/university/" + id), university);
            return getUniversityById(id);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode().value()), e.getResponseBodyAsString(), e);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteUniversity(Long id) {
        try {
            restTemplate.delete(new URI(dispatcherUrl + "/university/" + id));
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode().value()), e.getResponseBodyAsString(), e);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public CourseModule[] getAllCourseModules(Long universityId) {
        try {
            return restTemplate.getForObject(new URI(dispatcherUrl + "/university/" + universityId + "/coursemodule"), CourseModule[].class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode().value()), e.getResponseBodyAsString(), e);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            return new CourseModule[]{};
        }
    }

    public CourseModule getCourseModuleById(Long universityId, Long id) {
        try {
            return restTemplate.getForObject(new URI(dispatcherUrl + "/university/" + universityId + "/coursemodule/" + id), CourseModule.class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode().value()), e.getResponseBodyAsString(), e);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CourseModule createCourseModule(Long universityId, CourseModule courseModule) {
        try {
            return restTemplate.postForObject(new URI(dispatcherUrl + "/university/" + universityId + "/coursemodule"), courseModule, CourseModule.class);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode().value()), e.getResponseBodyAsString(), e);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CourseModule updateCourseModule(Long universityId, Long id, CourseModule courseModule) {
        try {
            restTemplate.put(new URI(dispatcherUrl + "/university/" + universityId + "/coursemodule/" + id), courseModule);
            return getCourseModuleById(universityId, id);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode().value()), e.getResponseBodyAsString(), e);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteCourseModule(Long universityId, Long id) {
        try {
            restTemplate.delete(new URI(dispatcherUrl + "/university/" + universityId + "/coursemodule/" + id));
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode().value()), e.getResponseBodyAsString(), e);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
