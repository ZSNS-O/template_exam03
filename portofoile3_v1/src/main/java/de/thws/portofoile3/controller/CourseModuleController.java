package de.thws.portofoile3.controller;

import de.thws.portofoile3.model.CourseModule;
import de.thws.portofoile3.service.CourseModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.stream.Collectors;

@RestController // this annotation make this class served restful endpoint
@RequestMapping (path="/coursemodule") // instead going to localhost:8080 it will go to the provided path
public class CourseModuleController {

    @Autowired  // dependency injection
    private final CourseModuleService courseModuleService;

    public CourseModuleController(CourseModuleService courseModuleService) {
        this.courseModuleService = courseModuleService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CourseModule>> findById(@PathVariable Long id) {
        CourseModule courseModule = courseModuleService.getCourseModuleById(id);
        if (courseModule == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<CourseModule> resource = EntityModel.of(courseModule);
        resource.add(linkTo(methodOn(CourseModuleController.class).findById(id)).withSelfRel());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(CourseModuleController.class).findById(id)).withSelfRel().toUri().toString());
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CourseModule>>> getAll(Pageable pageable) {
        Page<CourseModule> courseModulePage = courseModuleService.getAllCourseModules(pageable);
        PagedModel<EntityModel<CourseModule>> resources = (PagedModel<EntityModel<CourseModule>>) PagedModel.of(
                courseModulePage.map(courseModule -> EntityModel.of(courseModule,
                        linkTo(methodOn(CourseModuleController.class).findById(courseModule.getId())).withSelfRel())));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAll(pageable)).withSelfRel().toUri().toString());

        if (courseModulePage.hasPrevious()) {
            headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAll(courseModulePage.previousPageable())).withRel("prev").toUri().toString());
        }
        if (courseModulePage.hasNext()) {
            headers.add("Link", linkTo(methodOn(CourseModuleController.class).getAll(courseModulePage.nextPageable())).withRel("next").toUri().toString());
        }

        return new ResponseEntity<>(resources, headers, HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<EntityModel<CourseModule>> create(@RequestBody CourseModule courseModule) {
        CourseModule createdCourseModule = courseModuleService.createCourseModule(courseModule);
        EntityModel<CourseModule> resource = EntityModel.of(createdCourseModule);
        resource.add(linkTo(methodOn(CourseModuleController.class).findById(createdCourseModule.getId())).withSelfRel());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(CourseModuleController.class).findById(createdCourseModule.getId())).withSelfRel().toUri().toString());

        return ResponseEntity.created(linkTo(methodOn(CourseModuleController.class).findById(createdCourseModule.getId())).toUri()).headers(headers).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CourseModule>> updateCourseModule(@PathVariable Long id, @RequestBody CourseModule courseModule) {
        CourseModule updatedCourseModule = courseModuleService.updateCourseModule(id, courseModule);
        if (updatedCourseModule == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<CourseModule> resource = EntityModel.of(updatedCourseModule);
        resource.add(linkTo(methodOn(CourseModuleController.class).findById(updatedCourseModule.getId())).withSelfRel());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(CourseModuleController.class).findById(updatedCourseModule.getId())).withSelfRel().toUri().toString());

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseModule(@PathVariable("id") Long id) {
        courseModuleService.deleteCourseModule(id);
        return ResponseEntity.noContent().build();
    }
}
