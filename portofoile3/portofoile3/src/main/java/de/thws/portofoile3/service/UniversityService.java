package de.thws.portofoile3.service;

import de.thws.portofoile3.exceptions.ResourceNotFoundException;
import de.thws.portofoile3.model.University;
import de.thws.portofoile3.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UniversityService {

    private final UniversityRepository universityRepository;

    @Autowired // dependency injection
    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }



    public Page<University>getAllUniversity(Pageable pageable)
    {
        return universityRepository.findAll(pageable);
    }



    public University getUniversityById(Long uni_id)
    {
        return universityRepository.findById(uni_id).orElseThrow(()->
                new ResourceNotFoundException("University not found"));
    }

    public University createUniversity(University university)
    {
        return universityRepository.save(university);
    }

    public void deleteUniversityById(Long id)
    {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("University not found for this id :: " + id));
        universityRepository.delete(university);
    }

    public University updateUniversity(Long id,University universityDetails)
    {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("University not found for this id :: " + id));
        university.setUniversityName(universityDetails.getUniversityName());
        university.setCountry(universityDetails.getCountry());
        university.setDepartmentName(universityDetails.getDepartmentName());
        university.setDepartmentUrl(universityDetails.getDepartmentUrl());
        university.setContactPerson(universityDetails.getContactPerson());
        university.setStudentsWeCanSend(universityDetails.getStudentsWeCanSend());
        university.setStudentsWeCanAccept(universityDetails.getStudentsWeCanAccept());
        university.setNextSpringSemesterStart(universityDetails.getNextSpringSemesterStart());
        university.setNextAutumnSemesterStart(universityDetails.getNextAutumnSemesterStart());
        return universityRepository.save(university);
    }


    public Page<University> getUniversitiesByNameContaining(String universityName, Pageable pageable) {
        return universityRepository.findByNameContaining(universityName, pageable);
    }

    public Page<University> getUniversitiesByCountry(String country, Pageable pageable) {
        return universityRepository.findByCountry(country, pageable);
    }

    public Page<University> getUniversitiesByDepartmentName(String departmentName, Pageable pageable) {
        return universityRepository.findByDepartmentName(departmentName, pageable);
    }

    public Page<University> getUniversitiesByCountryAndDepartmentName(String country, String departmentName, Pageable pageable) {
        return universityRepository.findByCountryAndDepartmentName(country, departmentName, pageable);
    }

}



