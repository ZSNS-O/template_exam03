package de.thws.portfolio3.controller;

import de.thws.portfolio3.model.University;
import de.thws.portfolio3.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "api/v1/university")
public class UniversityController {

    private final UniversityService universityService;
    private final PagedResourcesAssembler<University> pagedResourcesAssembler;

    @Autowired
    public UniversityController(UniversityService universityService, PagedResourcesAssembler<University> pagedResourcesAssembler) {
        this.universityService = universityService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<University> universityOpt = universityService.getUniversityById(id);

        if (!universityOpt.isPresent()) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "University not found with ID " + id);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }

        University university = universityOpt.get();
        EntityModel<University> resource = EntityModel.of(university);
        resource.add(linkTo(methodOn(UniversityController.class).findById(id)).withSelfRel());
        resource.add(linkTo(methodOn(CourseModuleController.class).getAllByUni(id, Pageable.unpaged())).withRel("CourseModule"));

        WebMvcLinkBuilder updateLink = linkTo(methodOn(UniversityController.class).updateUni(id, null));
        WebMvcLinkBuilder deleteLink = linkTo(methodOn(UniversityController.class).deleteUni(id));
        Pageable defaultPageable = PageRequest.of(0, 10);
        WebMvcLinkBuilder getAllLink = linkTo(methodOn(UniversityController.class).getAll( defaultPageable));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", "<" + updateLink.withRel("updateUni").toUri().toString() + ">; rel=\"update\"");
        headers.add("Link", "<" + deleteLink.withRel("deleteUni").toUri().toString() + ">; rel=\"delete\"");
        headers.add("Link", "<" + getAllLink.withRel("getAll").toUri().toString() + ">; rel=\"getAll\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }



    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<University>>> getAll(Pageable pageable) {
        Page<University> universityPage = universityService.getAllUniversities(pageable);
        PagedModel<EntityModel<University>> resources = pagedResourcesAssembler.toModel(universityPage, university -> {
            EntityModel<University> resource = EntityModel.of(university);


            resource.add(linkTo(methodOn(UniversityController.class).findById(university.getId())).withSelfRel());
            resource.add(linkTo(methodOn(CourseModuleController.class).getAllByUni(university.getId(), Pageable.unpaged())).withRel("CourseModule"));
            return resource;
        });

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(pageable)).withSelfRel().toUri().toString());

        if (universityPage.hasPrevious()) {
            headers.add("Link", "<" + linkTo(methodOn(UniversityController.class).getAll(universityPage.previousPageable())).withRel("prev").toUri().toString() + ">; rel=\"prev\"");
        }
        if (universityPage.hasNext()) {
            headers.add("Link", "<" + linkTo(methodOn(UniversityController.class).getAll(universityPage.nextPageable())).withRel("next").toUri().toString() + ">; rel=\"next\"");
        }

        headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(Pageable.unpaged().first())).withRel("first").toUri().toString()+ ">; rel=\"next\"");
        headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(Pageable.unpaged())).withRel("last").toUri().toString()+ ">; rel=\"prev\"");

        return new ResponseEntity<>(resources, headers, HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "order", defaultValue = "asc") String order) {

        List<University> universities = universityService.searchUniversities(name, order);
        List<EntityModel<University>> resources = universities.stream()
                .map(university -> EntityModel.of(university,
                        linkTo(methodOn(UniversityController.class).findById(university.getId())).withSelfRel(),
                        linkTo(methodOn(CourseModuleController.class).getAllByUni(university.getId(), Pageable.unpaged())).withRel("courseModules")))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("universities", resources);

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(CourseModuleController.class).getAllByUni(universities.get(0).getId(), Pageable.unpaged())).withRel("self"));
        links.add(linkTo(methodOn(CourseModuleController.class).getAllByUni(universities.get(0).getId(), Pageable.unpaged().first())).withRel("first"));
        links.add(linkTo(methodOn(CourseModuleController.class).getAllByUni(universities.get(0).getId(), Pageable.unpaged())).withRel("last"));

        // Optionally, add prev and next if applicable
        if (!universities.isEmpty()) {
            University firstUniversity = universities.get(0);
            University lastUniversity = universities.get(universities.size() - 1);

            links.add(linkTo(methodOn(UniversityController.class).search(name, "asc")).withRel("prev"));
            links.add(linkTo(methodOn(UniversityController.class).search(name, "desc")).withRel("next"));
        }

        response.put("links", links);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }







    @PostMapping
    public ResponseEntity<EntityModel<University>> create(@RequestBody University university) {
        University createdUniversity = universityService.createUniversity(university);
        EntityModel<University> resource = EntityModel.of(createdUniversity);

        WebMvcLinkBuilder selfLink = linkTo(methodOn(UniversityController.class).findById(createdUniversity.getId()));

        Pageable defaultPageable = PageRequest.of(0, 10);
        WebMvcLinkBuilder getAllLink = linkTo(methodOn(UniversityController.class).getAll( defaultPageable));

        resource.add(selfLink.withSelfRel());
        resource.add(linkTo(methodOn(CourseModuleController.class).getAllByUni(createdUniversity.getId(), Pageable.unpaged())).withRel("CourseModule"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", "<" + getAllLink.withRel("getAll").toUri().toString() + ">; rel=\"getAll\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUni(@PathVariable Long id, @RequestBody University universityInfos) {
        Optional<University> updatedUniversityOpt = universityService.updateUniversity(id, universityInfos);

        if (!updatedUniversityOpt.isPresent()) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "University not found with ID " + id);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }

        University updatedUniversity = updatedUniversityOpt.get();
        EntityModel<University> resource = EntityModel.of(updatedUniversity);
        resource.add(linkTo(methodOn(UniversityController.class).findById(updatedUniversity.getId())).withSelfRel());
        resource.add(linkTo(methodOn(CourseModuleController.class).getAllByUni(updatedUniversity.getId(), Pageable.unpaged())).withRel("CourseModule"));

        HttpHeaders headers = new HttpHeaders();
        //headers.add("Link", linkTo(methodOn(UniversityController.class).findById(updatedUniversity.getId())).withSelfRel().toUri().toString());

        WebMvcLinkBuilder updateLink = linkTo(methodOn(UniversityController.class).updateUni(id, null));

        Pageable defaultPageable = PageRequest.of(0, 10);
        WebMvcLinkBuilder getAllLink = linkTo(methodOn(UniversityController.class).getAll( defaultPageable));


        headers.add("Link", "<" + updateLink.withRel("updateUni").toUri().toString() + ">; rel=\"update\"");
        headers.add("Link", "<" + getAllLink.withRel("getAll").toUri().toString() + ">; rel=\"getAll\"");


        return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUni(@PathVariable("id") Long id) {
        boolean deleted = universityService.deleteUniversityById(id);

        if (!deleted) {
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "University not found with ID " + id);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        Pageable defaultPageable = PageRequest.of(0, 10);
        WebMvcLinkBuilder getAllLink = linkTo(methodOn(UniversityController.class).getAll( defaultPageable));
        headers.add("Link", "<" + getAllLink.toUri().toString() + ">; rel=\"getAll\"");

        return ResponseEntity.noContent().headers(headers).build();
    }


}
