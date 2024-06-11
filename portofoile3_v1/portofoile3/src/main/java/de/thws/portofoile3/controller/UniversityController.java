package de.thws.portofoile3.controller;

import de.thws.portofoile3.model.University;
import de.thws.portofoile3.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(path = "api/v1/university")
public class UniversityController {

    private final UniversityService universityService;
    private final PagedResourcesAssembler<University> pagedResourcesAssembler;


    @Autowired
    public UniversityController(UniversityService universityService,PagedResourcesAssembler<University>  pagedResourcesAssembler) {
        this.universityService = universityService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<University>> findById(@PathVariable Long id) {
        University university = universityService.getUniversityById(id);
        if (university == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<University> resource = EntityModel.of(university);
        resource.add(linkTo(methodOn(UniversityController.class).findById(id)).withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<University>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "10") int size,
                                                                      @RequestParam(defaultValue = "name") String sortBy,
                                                                      @RequestParam(defaultValue = "asc") String sortDir) {

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<University> universitiesPage = universityService.getAllUniversity(pageable);

        PagedModel<EntityModel<University>> resources = pagedResourcesAssembler.toModel(universitiesPage, this::toModel);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(page, size, sortBy, sortDir)).withSelfRel().toUri().toString());

        if (universitiesPage.hasPrevious()) {
            headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(universitiesPage.previousPageable().getPageNumber(), size, sortBy, sortDir)).withRel("prev").toUri().toString());
        }
        if (universitiesPage.hasNext()) {
            headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(universitiesPage.nextPageable().getPageNumber(), size, sortBy, sortDir)).withRel("next").toUri().toString());
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
            universityPage = universityService.findByUniversityNameContaining(universityName, pageable);
        } else if (country != null && departmentName != null) {
            universityPage = universityService.findUniversitiesByCountryAndDepartmentName(country, departmentName, pageable);
        } else if (country != null) {
            universityPage = universityService.findUniversitiesByCountry(country, pageable);
        } else if (departmentName != null) {
            universityPage = universityService.findUniversitiesByDepartmentName(departmentName, pageable);
        } else {
            universityPage = universityService.getAllUniversity(pageable);
        }

        PagedModel<EntityModel<University>> pagedModel = pagedResourcesAssembler.toModel(universityPage, this::toModel);

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

        Pageable pageableAsc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.asc("name")));
        Pageable pageableDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("name")));

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

    private EntityModel<University> toModel(University university) {
        return EntityModel.of(university,
                linkTo(methodOn(UniversityController.class).findById(university.getId())).withSelfRel());
    }


}
