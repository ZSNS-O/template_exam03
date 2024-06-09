package de.thws.portofoile3.front;

import de.thws.portofoile3.model.CourseModule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
public class CourseModuleClient {

    private final RestTemplate restTemplate;
    private final String dispatcherUrl;

    public CourseModuleClient(String dispatcherUrl) {
        this.restTemplate = new RestTemplate();
        this.dispatcherUrl = dispatcherUrl;
    }

    public CourseModule[] getAllModules() {
        String url = dispatcherUrl + "/module";
        ResponseEntity<CourseModule[]> response = restTemplate.getForEntity(url, CourseModule[].class);
        return response.getBody();
    }

    public CourseModule getModuleById(Long id) {
        String url = dispatcherUrl + "/module/" + id;
        ResponseEntity<CourseModule> response = restTemplate.getForEntity(url, CourseModule.class);
        return response.getBody();
    }

    public CourseModule createModule(CourseModule module) {
        String url = dispatcherUrl + "/modules";
        ResponseEntity<CourseModule> response = restTemplate.postForEntity(url, module, CourseModule.class);
        return response.getBody();
    }

    public CourseModule updateModule(Long id, CourseModule moduleDetails) {
        String url = dispatcherUrl + "/module/" + id;
        restTemplate.put(url, moduleDetails);
        return getModuleById(id);
    }

    public void deleteModule(Long id) {
        String url = dispatcherUrl + "/module/" + id;
        restTemplate.delete(url);
    }
}
