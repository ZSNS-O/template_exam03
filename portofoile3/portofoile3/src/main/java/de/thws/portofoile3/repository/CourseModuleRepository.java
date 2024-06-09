package de.thws.portofoile3.repository;

import de.thws.portofoile3.model.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // this interface is responsible for data access
public interface CourseModuleRepository extends JpaRepository<CourseModule,Long> {


}
