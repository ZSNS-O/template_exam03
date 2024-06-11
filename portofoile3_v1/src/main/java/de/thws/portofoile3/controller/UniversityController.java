package de.thws.portofoile3.controller;

import de.thws.portofoile3.model.University;
import de.thws.portofoile3.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "api/v1/university")
public class UniversityController {

    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<University>> findById(@PathVariable Long id) {
        University university = universityService.getUniversityById(id);
        if (university == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<University> resource = EntityModel.of(university);
        resource.add(linkTo(methodOn(UniversityController.class).findById(id)).withSelfRel());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(UniversityController.class).findById(id)).withSelfRel().toUri().toString());
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<University>>> getAll(Pageable pageable) {
        Page<University> universitiesPage = universityService.getAllUniversity(pageable);
        PagedModel<EntityModel<University>> resources = (PagedModel<EntityModel<University>>) PagedModel.of(
                universitiesPage.map(university -> EntityModel.of(university,
                        linkTo(methodOn(UniversityController.class).findById(university.getId())).withSelfRel())));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(pageable)).withSelfRel().toUri().toString());

        if (universitiesPage.hasPrevious()) {
            headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(universitiesPage.previousPageable())).withRel("prev").toUri().toString());
        }
        if (universitiesPage.hasNext()) {
            headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(universitiesPage.nextPageable())).withRel("next").toUri().toString());
        }

        return new ResponseEntity<>(resources, headers, HttpStatus.OK);
    }



    @GetMapping("/search")
    public ResponseEntity<PagedModel<EntityModel<University>>> searchUniversities(
            @RequestParam(required = false) String universityName,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String departmentName,
            Pageable pageable) {

        Page<University> universityPage;

        if (universityName != null) {
            universityPage = universityService.getUniversitiesByNameContaining(universityName, pageable);
        } else if (country != null && departmentName != null) {
            universityPage = universityService.getUniversitiesByCountryAndDepartmentName(country, departmentName, pageable);
        } else if (country != null) {
            universityPage = universityService.getUniversitiesByCountry(country, pageable);
        } else if (departmentName != null) {
            universityPage = universityService.getUniversitiesByDepartmentName(departmentName, pageable);
        } else {
            universityPage = universityService.getAllUniversity(pageable);
        }

        PagedModel<EntityModel<University>> pagedModel = (PagedModel<EntityModel<University>>) PagedModel.of(
                universityPage.map(university -> EntityModel.of(university,
                        linkTo(methodOn(UniversityController.class).findById(university.getId())).withSelfRel())));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(UniversityController.class).searchUniversities(
                universityName, country, departmentName, pageable)).withSelfRel().toUri().toString());

        if (universityPage.hasPrevious()) {
            headers.add("Link", linkTo(methodOn(UniversityController.class).searchUniversities(
                    universityName, country, departmentName, pageable.previousOrFirst())).withRel("prev").toUri().toString());
        }
        if (universityPage.hasNext()) {
            headers.add("Link", linkTo(methodOn(UniversityController.class).searchUniversities(
                    universityName, country, departmentName, pageable.next())).withRel("next").toUri().toString());
        }

        // Manually create the sort orders for ascending and descending
        Pageable pageableAsc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.asc("universityName")));
        Pageable pageableDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("universityName")));

        headers.add("Link", linkTo(methodOn(UniversityController.class).searchUniversities(
                universityName, country, departmentName, pageableAsc)).withRel("asc").toUri().toString());
        headers.add("Link", linkTo(methodOn(UniversityController.class).searchUniversities(
                universityName, country, departmentName, pageableDesc)).withRel("desc").toUri().toString());

        return new ResponseEntity<>(pagedModel, headers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EntityModel<University>> create(@RequestBody University university) {
        University createdUniversity = universityService.createUniversity(university);
        EntityModel<University> resource = EntityModel.of(createdUniversity);
        resource.add(linkTo(methodOn(UniversityController.class).findById(createdUniversity.getId())).withSelfRel());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(UniversityController.class).findById(createdUniversity.getId())).withSelfRel().toUri().toString());

        return ResponseEntity.created(linkTo(methodOn(UniversityController.class).findById(createdUniversity.getId())).toUri()).headers(headers).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<University>> updateUni(@PathVariable Long id, @RequestBody University university) {
        University updatedUniversity = universityService.updateUniversity(id, university);
        if (updatedUniversity == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<University> resource = EntityModel.of(updatedUniversity);
        resource.add(linkTo(methodOn(UniversityController.class).findById(updatedUniversity.getId())).withSelfRel());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(UniversityController.class).findById(updatedUniversity.getId())).withSelfRel().toUri().toString());

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUni(@PathVariable("id") Long id) {
        universityService.deleteUniversityById(id);
        return ResponseEntity.noContent().build();
    }
}
