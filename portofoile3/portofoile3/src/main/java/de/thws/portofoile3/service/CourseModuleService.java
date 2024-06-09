package de.thws.portofoile3.service;

import de.thws.portofoile3.exceptions.ResourceNotFoundException;
import de.thws.portofoile3.model.CourseModule;
import de.thws.portofoile3.repository.CourseModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseModuleService {

    private final CourseModuleRepository courseModuleRepository;

    @Autowired // dependency injection
    public CourseModuleService(CourseModuleRepository courseModuleRepository) {
        this.courseModuleRepository = courseModuleRepository;
    }

    public CourseModule createCourseModule(CourseModule courseModule) {
        return courseModuleRepository.save(courseModule);
    }

    public Page<CourseModule> getAllCourseModules(Pageable pageable) {
        return courseModuleRepository.findAll(pageable);
    }

    public CourseModule getCourseModuleById(Long id) {
        return courseModuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseModule not found for this id :: " + id));

    }

    public void deleteCourseModule(Long id) {
        CourseModule courseModule = courseModuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseModule not found for this id :: " + id));
        courseModuleRepository.delete(courseModule);
    }

    public CourseModule updateCourseModule(Long id, CourseModule courseModuleDetails) {
        CourseModule courseModule = courseModuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseModule not found for this id :: " + id));

        courseModule.setModuleName(courseModuleDetails.getModuleName());
        courseModule.setSemester(courseModuleDetails.getSemester());
        courseModule.setCreditPoints(courseModuleDetails.getCreditPoints());
        return courseModuleRepository.save(courseModule);
    }

    }

