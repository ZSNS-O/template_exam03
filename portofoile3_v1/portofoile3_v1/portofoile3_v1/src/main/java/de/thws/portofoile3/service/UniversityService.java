package de.thws.portofoile3.service;

import de.thws.portofoile3.exceptions.ResourceNotFoundException;
import de.thws.portofoile3.model.CourseModule;
import de.thws.portofoile3.model.University;
import de.thws.portofoile3.repository.CourseModuleRepository;
import de.thws.portofoile3.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {

    private final UniversityRepository universityRepository;
    private final CourseModuleRepository courseModuleRepository;

    @Autowired // dependency injection
    public UniversityService(UniversityRepository universityRepository, CourseModuleRepository courseModuleRepository) {
        this.universityRepository = universityRepository;
        this.courseModuleRepository = courseModuleRepository;
    }

    public Page<University> getAllUniversity(Pageable pageable) {
        return universityRepository.findAll(pageable);
    }

    public Page<University> getAllUniversities(String search, String order, Pageable pageable) {
        Sort sort = Sort.by("name");
        if ("desc".equalsIgnoreCase(order)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if (search != null && !search.isEmpty()) {
            return universityRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            return universityRepository.findAll(pageable);
        }
    }

    public Optional<University> getUniversityById(Long uni_id) {
        return universityRepository.findById(uni_id);
    }


    public University createUniversity(University university) {
        return universityRepository.save(university);
    }

    public void deleteUniversityById(Long id) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("University not found for this id :: " + id));
        universityRepository.delete(university);
    }

    @Transactional
    public University updateUniversity(Long id, University universityInfos) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("University not found for this id :: " + id));
        university.setUniversityName(universityInfos.getName());
        university.setCountry(universityInfos.getCountry());
        university.setDepartmentName(universityInfos.getDepartmentName());
        university.setDepartmentUrl(universityInfos.getDepartmentUrl());
        university.setContactPerson(universityInfos.getContactPerson());
        university.setStudentsWeCanSend(universityInfos.getStudentsWeCanSend());
        university.setStudentsWeCanAccept(universityInfos.getStudentsWeCanAccept());
        university.setNextSpringSemesterStart(universityInfos.getNextSpringSemesterStart());
        university.setNextAutumnSemesterStart(universityInfos.getNextAutumnSemesterStart());


        return universityRepository.save(university);
    }


}
