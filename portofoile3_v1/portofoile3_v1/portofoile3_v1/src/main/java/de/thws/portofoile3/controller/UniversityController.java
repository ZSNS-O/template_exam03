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

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<University> universityOpt = universityService.getUniversityById(id);

        if (!universityOpt.isPresent()) {
            // Create a custom response body
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "University not found with ID " + id);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }

        University university = universityOpt.get();
        EntityModel<University> resource = EntityModel.of(university);
        resource.add(linkTo(methodOn(UniversityController.class).findById(id)).withSelfRel());

        WebMvcLinkBuilder updateLink = linkTo(methodOn(UniversityController.class).updateUni(id, null));
        WebMvcLinkBuilder deleteLink = linkTo(methodOn(UniversityController.class).deleteUni(id));
        String defaultSearch = "";
        String defaultOrder = "asc";
        Pageable defaultPageable = PageRequest.of(0, 10);
        WebMvcLinkBuilder getAllLink = linkTo(methodOn(UniversityController.class).getAll(defaultSearch, defaultOrder, defaultPageable));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", "<" + updateLink.withRel("updateUni").toUri().toString() + ">; rel=\"update\"");
        headers.add("Link", "<" + deleteLink.withRel("deleteUni").toUri().toString() + ">; rel=\"delete\"");
        headers.add("Link", "<" + getAllLink.withRel("getAll").toUri().toString() + ">; rel=\"getAll\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<University>>> getAll(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "order", defaultValue = "asc") String order,
            Pageable pageable) {
        Page<University> universityPage = universityService.getAllUniversities(search, order, pageable);
        PagedModel<EntityModel<University>> resources = pagedResourcesAssembler.toModel(universityPage, university -> EntityModel.of(university,
                linkTo(methodOn(UniversityController.class).findById(university.getId())).withSelfRel()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(search, order, pageable)).withSelfRel().toUri().toString());

        if (universityPage.hasPrevious()) {
            headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(search, order, universityPage.previousPageable())).withRel("prev").toUri().toString());
        }
        if (universityPage.hasNext()) {
            headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(search, order, universityPage.nextPageable())).withRel("next").toUri().toString());
        }

        // Add links for ordering
        headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(search, "asc", pageable)).withRel("order_asc").toUri().toString());
        headers.add("Link", linkTo(methodOn(UniversityController.class).getAll(search, "desc", pageable)).withRel("order_desc").toUri().toString());

        return new ResponseEntity<>(resources, headers, HttpStatus.OK);
    }








    @PostMapping
    public ResponseEntity<EntityModel<University>> create(@RequestBody University university) {
        University createdUniversity = universityService.createUniversity(university);
        EntityModel<University> resource = EntityModel.of(createdUniversity);

        WebMvcLinkBuilder selfLink = linkTo(methodOn(UniversityController.class).findById(createdUniversity.getId()));
        String defaultSearch = "";
        String defaultOrder = "asc";
        Pageable defaultPageable = PageRequest.of(0, 10);
        WebMvcLinkBuilder getAllLink = linkTo(methodOn(UniversityController.class).getAll(defaultSearch, defaultOrder, defaultPageable));

        // Adding links to the resource
        resource.add(selfLink.withSelfRel());


        // Adding links to the headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Link", "<" + getAllLink.withRel("getAll").toUri().toString() + ">; rel=\"getAll\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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

        HttpHeaders headers = new HttpHeaders();
        String defaultSearch = "";
        String defaultOrder = "asc";
        Pageable defaultPageable = PageRequest.of(0, 10);
        WebMvcLinkBuilder getAllLink = linkTo(methodOn(UniversityController.class).getAll(defaultSearch, defaultOrder, defaultPageable));
       headers.add("Link", "<" + getAllLink.toUri().toString() + ">; rel=\"getAll\"");

        return ResponseEntity.noContent().headers(headers).build();
    }


    private EntityModel<University> toModel(University university) {
        return EntityModel.of(university,
                linkTo(methodOn(UniversityController.class).findById(university.getId())).withSelfRel());
    }


}
