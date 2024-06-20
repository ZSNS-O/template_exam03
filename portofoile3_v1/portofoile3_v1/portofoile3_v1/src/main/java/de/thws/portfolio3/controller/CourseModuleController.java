package de.thws.portfolio3.controller;

import de.thws.portfolio3.model.CourseModule;
import de.thws.portfolio3.service.CourseModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "api/v1/university/{universityId}/coursemodule")
public class CourseModuleController {

    private final CourseModuleService courseModuleService;
    private final PagedResourcesAssembler<CourseModule> pagedResourcesAssembler;

    @Autowired
    public CourseModuleController(CourseModuleService courseModuleService, PagedResourcesAssembler<CourseModule> pagedResourcesAssembler) {
        this.courseModuleService = courseModuleService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long universityId, @PathVariable Long id) {
        Optional<CourseModule> courseModuleOpt = courseModuleService.getCourseModuleById(universityId, id);

        if (courseModuleOpt.isPresent()) {
            CourseModule courseModule = courseModuleOpt.get();
            EntityModel<CourseModule> resource = EntityModel.of(courseModule,
                    linkTo(methodOn(CourseModuleController.class).findById(universityId, id)).withSelfRel(),
                    linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged())).withRel("courseModules"));

            return ResponseEntity.ok(resource);
        } else {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "CourseModule not found with ID " + id);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCourseModule(@PathVariable Long universityId, @RequestBody CourseModule courseModule) {
        Optional<CourseModule> createdCourseModuleOpt = courseModuleService.createCourseModule(universityId, courseModule);

        if (createdCourseModuleOpt.isPresent()) {
            CourseModule createdCourseModule = createdCourseModuleOpt.get();
            EntityModel<CourseModule> resource = EntityModel.of(createdCourseModule,
                    linkTo(methodOn(CourseModuleController.class).findById(universityId, createdCourseModule.getId())).withSelfRel());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged())).withRel("courseModules").toUri().toString());


            return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
        } else {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "University not found with ID " + universityId);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCourseModule(@PathVariable Long universityId, @PathVariable Long id) {
        boolean isDeleted = courseModuleService.deleteCourseModulesByUniversityId(universityId, id);

        Map<String, String> responseBody = new HashMap<>();

        if (!isDeleted) {
            responseBody.put("message", "CourseModule not found with ID " + id);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }

        responseBody.put("message", "CourseModule successfully deleted");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged())).withRel("courseModules").toUri().toString());

        return new ResponseEntity<>( headers, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourseModuleByUniversityId(@PathVariable Long universityId, @PathVariable Long id, @RequestBody CourseModule courseModule) {
        Optional<CourseModule> updatedCourseModuleOpt = courseModuleService.updateCourseModuleByUniversityId(universityId, id, courseModule);

        if (!updatedCourseModuleOpt.isPresent()) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "CourseModule not found with ID " + id);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }

        CourseModule updatedCourseModule = updatedCourseModuleOpt.get();
        EntityModel<CourseModule> resource = EntityModel.of(updatedCourseModule,
                linkTo(methodOn(CourseModuleController.class).findById(universityId, updatedCourseModule.getId())).withSelfRel(),
                linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged())).withRel("courseModules"));

        return ResponseEntity.ok(resource);
    }

    @GetMapping
        public ResponseEntity<PagedModel<EntityModel<CourseModule>>> getAllByUni(@PathVariable Long universityId, Pageable pageable) {
        Page<CourseModule> courseModulePage = courseModuleService.getAllCourseModulesByUniversityId(universityId, pageable);
        PagedModel<EntityModel<CourseModule>> resources = pagedResourcesAssembler.toModel(courseModulePage, courseModule -> {
            EntityModel<CourseModule> resource = EntityModel.of(courseModule);
            resource.add(linkTo(methodOn(CourseModuleController.class).findById(universityId, courseModule.getId())).withSelfRel());
            resource.add(linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged())).withRel("courseModules"));
            return resource;
        });

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, pageable)).withSelfRel().toUri().toString());

        if (courseModulePage.hasPrevious()) {
            headers.add("Link", "<" + linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, courseModulePage.previousPageable())).withRel("prev").toUri().toString() + ">; rel=\"prev\"");
        }
        if (courseModulePage.hasNext()) {
            headers.add("Link", "<" + linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, courseModulePage.nextPageable())).withRel("next").toUri().toString() + ">; rel=\"next\"");
        }

        headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged().first())).withRel("first").toUri().toString());
        headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged())).withRel("last").toUri().toString());

        resources.add(linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged().first())).withRel("first"));
        resources.add(linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged())).withRel("last"));

        return new ResponseEntity<>(resources, headers, HttpStatus.OK);
    }







}
