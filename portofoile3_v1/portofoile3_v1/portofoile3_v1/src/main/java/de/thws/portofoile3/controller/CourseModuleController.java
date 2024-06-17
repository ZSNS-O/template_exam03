package de.thws.portofoile3.controller;

import de.thws.portofoile3.model.CourseModule;
import de.thws.portofoile3.service.CourseModuleService;
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

    @Autowired
    private final CourseModuleService courseModuleService;
    private final PagedResourcesAssembler<CourseModule> pagedResourcesAssembler;

    public CourseModuleController(CourseModuleService courseModuleService, PagedResourcesAssembler<CourseModule> pagedResourcesAssembler) {
        this.courseModuleService = courseModuleService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public ResponseEntity<EntityModel<CourseModule>> findById(@PathVariable Long universityId, @PathVariable Long id) {
        Optional<CourseModule> courseModuleOpt = courseModuleService.getCourseModuleById(universityId, id);

        if (courseModuleOpt.isPresent()) {
            CourseModule courseModule = courseModuleOpt.get();
            EntityModel<CourseModule> resource = EntityModel.of(courseModule,
                    linkTo(methodOn(CourseModuleController.class).findById(universityId, id)).withSelfRel(),
                    linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged())).withRel("courseModules"));

            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CourseModule>>> getAllByUni(@PathVariable Long universityId, Pageable pageable) {
        Page<CourseModule> courseModulePage = courseModuleService.getAllCourseModulesByUniversityId(universityId, pageable);
        PagedModel<EntityModel<CourseModule>> resources = pagedResourcesAssembler.toModel(courseModulePage, courseModule ->
                EntityModel.of(courseModule,
                        linkTo(methodOn(CourseModuleController.class).findById(universityId, courseModule.getId())).withSelfRel()
                )
        );

        HttpHeaders headers = new HttpHeaders();
        // Adding the self link to the headers as required
        headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, pageable)).withSelfRel().toUri().toString());

        // Adding the prev and next links to the headers
        if (courseModulePage.hasPrevious()) {
            headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, courseModulePage.previousPageable())).withRel("prev").toUri().toString());
        }
        if (courseModulePage.hasNext()) {
            headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, courseModulePage.nextPageable())).withRel("next").toUri().toString());
        }

        return new ResponseEntity<>(resources, headers, HttpStatus.OK);
    }



    public ResponseEntity<Map<String, String>> deleteCourseModule(@PathVariable Long id) {
        boolean isDeleted = courseModuleService.deleteCourseModule(id);

        Map<String, String> responseBody = new HashMap<>();

        if (!isDeleted) {
            // CourseModule not found
            responseBody.put("message", "CourseModule not found with ID " + id);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }

        // Successful deletion
        responseBody.put("message", "CourseModule successfully deleted");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CourseModule>> updateCourseModuleByUniversityId(@PathVariable Long universityId, @PathVariable Long id, @RequestBody CourseModule courseModule) {
        CourseModule updatedCourseModule = courseModuleService.updateCourseModuleByUniversityId(universityId, id, courseModule);
        EntityModel<CourseModule> resource = EntityModel.of(updatedCourseModule,
                linkTo(methodOn(CourseModuleController.class).findById(universityId, updatedCourseModule.getId())).withSelfRel(),
                linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged())).withRel("courseModules"));

        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<EntityModel<CourseModule>> createCourseModule(@PathVariable Long universityId, @RequestBody CourseModule courseModule) {
        CourseModule createdCourseModule = courseModuleService.createCourseModule(universityId, courseModule);
        EntityModel<CourseModule> resource = EntityModel.of(createdCourseModule,
                linkTo(methodOn(CourseModuleController.class).findById(universityId, createdCourseModule.getId())).withSelfRel());

        HttpHeaders headers = new HttpHeaders();
        // Adding the link to the collection to the headers
        headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAllByUni(universityId, Pageable.unpaged())).withRel("courseModules").toUri().toString());

        return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
    }

}
