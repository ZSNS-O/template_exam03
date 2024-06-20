package de.thws.portfolio3.service;


import de.thws.portfolio3.model.University;
import de.thws.portfolio3.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {

    private final UniversityRepository universityRepository;


    @Autowired
    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;

    }




    public List<University> searchUniversities(String name, String order) {
        Sort sort = Sort.by("name");
        if ("desc".equalsIgnoreCase(order)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        return universityRepository.findByNameContainingIgnoreCase(name, sort);
    }


    public Page<University> getAllUniversities(Pageable pageable) {
        return universityRepository.findAll(pageable);
    }




    public Optional<University> getUniversityById(Long uniId) {
        return universityRepository.findById(uniId);
    }

    public University createUniversity(University university) {
        return universityRepository.save(university);
    }

    public boolean deleteUniversityById(Long id) {
        Optional<University> universityOpt = universityRepository.findById(id);
        if (universityOpt.isPresent()) {
            universityRepository.delete(universityOpt.get());
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Optional<University> updateUniversity(Long id, University universityInfos) {
        return universityRepository.findById(id).map(university -> {
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
        });
    }
}
