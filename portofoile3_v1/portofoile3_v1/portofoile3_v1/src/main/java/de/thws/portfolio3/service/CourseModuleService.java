package de.thws.portfolio3.service;

import de.thws.portfolio3.model.CourseModule;
import de.thws.portfolio3.model.University;
import de.thws.portfolio3.repository.CourseModuleRepository;
import de.thws.portfolio3.repository.UniversityRepository;
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

    @Autowired
    public CourseModuleService(CourseModuleRepository courseModuleRepository, UniversityRepository universityRepository) {
        this.courseModuleRepository = courseModuleRepository;
        this.universityRepository = universityRepository;
    }

    public Optional<CourseModule> getCourseModuleById(Long universityId, Long id) {
        return courseModuleRepository.findByIdAndUniversityId(id, universityId);
    }

    public Optional<CourseModule> createCourseModule(Long universityId, CourseModule courseModule) {
        Optional<University> universityOpt = universityRepository.findById(universityId);

        if (universityOpt.isPresent()) {
            courseModule.setUniversity(universityOpt.get());
            return Optional.of(courseModuleRepository.save(courseModule));
        } else {
            return Optional.empty();
        }
    }

    public Page<CourseModule> getAllCourseModulesByUniversityId(Long universityId, Pageable pageable) {
        return courseModuleRepository.findAllByUniversityId(universityId, pageable);
    }

    @Transactional
    public boolean deleteCourseModulesByUniversityId(Long universityId, Long id) {
        Optional<CourseModule> courseModuleOpt = getCourseModuleById(universityId, id);

        if (!courseModuleOpt.isPresent()) {
            return false;
        }

        courseModuleRepository.deleteByIdAndUniversityId(id, universityId);
        return true;
    }

    @Transactional
    public Optional<CourseModule> updateCourseModuleByUniversityId(Long universityId, Long id, CourseModule courseModuleDetails) {
        return courseModuleRepository.findByIdAndUniversityId(id, universityId).map(courseModule -> {
            courseModule.setModuleName(courseModuleDetails.getModuleName());
            courseModule.setSemester(courseModuleDetails.getSemester());
            courseModule.setCreditPoints(courseModuleDetails.getCreditPoints());
            if (courseModuleDetails.getUniversity() != null) {
                courseModule.setUniversity(courseModuleDetails.getUniversity());
            }
            return courseModuleRepository.save(courseModule);
        });
    }
}
