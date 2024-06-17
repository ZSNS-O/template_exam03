package de.thws.portofoile3.service;

import de.thws.portofoile3.exceptions.ResourceNotFoundException;
import de.thws.portofoile3.model.CourseModule;
import de.thws.portofoile3.model.University;
import de.thws.portofoile3.repository.CourseModuleRepository;
import de.thws.portofoile3.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class CourseModuleService {

    private final CourseModuleRepository courseModuleRepository;
    private final UniversityRepository universityRepository;

    @Autowired // dependency injection
    public CourseModuleService(CourseModuleRepository courseModuleRepository, UniversityRepository universityRepository) {
        this.courseModuleRepository = courseModuleRepository;
        this.universityRepository = universityRepository;
    }


    public CourseModule createCourseModule(Long universityId, CourseModule courseModule) {
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new ResourceNotFoundException("University not found for this id :: " + universityId));

        courseModule.setUniversity(university);
        return courseModuleRepository.save(courseModule);
    }


    public Optional<CourseModule> getCourseModuleById(Long id) {
        return courseModuleRepository.findById(id);
    }


    public boolean deleteCourseModule(Long id) {
        Optional<CourseModule> courseModuleOpt = courseModuleRepository.findById(id);

        if (!courseModuleOpt.isPresent()) {
            // CourseModule not found
            return false;
        }

        courseModuleRepository.delete(courseModuleOpt.get());
        return true;
    }


    public CourseModule updateCourseModule(Long id, CourseModule courseModuleDetails) {
        CourseModule courseModule = courseModuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseModule not found for this id :: " + id));

        courseModule.setModuleName(courseModuleDetails.getModuleName());
        courseModule.setSemester(courseModuleDetails.getSemester());
        courseModule.setCreditPoints(courseModuleDetails.getCreditPoints());
        courseModule.setUniversity(courseModuleDetails.getUniversity());
        return courseModuleRepository.save(courseModule);
    }
    public Page<CourseModule> getAllCourseModulesByUniversityId(Long universityId, Pageable pageable) {
        return courseModuleRepository.findAllByUniversityId(universityId, pageable);
    }

    public Page<CourseModule> getAllCourseModules(Pageable pageable) {
        return courseModuleRepository.findAll(pageable);
    }


    public Optional<CourseModule> getCourseModuleById(Long universityId, Long id) {
        return courseModuleRepository.findByIdAndUniversityId(id, universityId);
    }


    @Transactional
    public void deleteCourseModulesByUniversityId(Long universityId, Long id) {
        Optional<CourseModule> courseModule = getCourseModuleById(universityId, id);
        courseModuleRepository.deleteByIdAndUniversityId(id, universityId);
    }

    @Transactional
    public CourseModule updateCourseModuleByUniversityId(Long universityId, Long id, CourseModule courseModuleDetails) {
        CourseModule courseModule = courseModuleRepository.findByIdAndUniversityId(id, universityId)
                .orElseThrow(() -> new ResourceNotFoundException("CourseModule not found for this id and university id :: " + id + ", " + universityId));

        courseModule.setModuleName(courseModuleDetails.getModuleName());
        courseModule.setSemester(courseModuleDetails.getSemester());
        courseModule.setCreditPoints(courseModuleDetails.getCreditPoints());
        return courseModuleRepository.save(courseModule);
    }




}

