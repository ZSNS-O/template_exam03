    package de.thws.portfolio3.repository;

    import de.thws.portfolio3.model.CourseModule;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;


    import java.util.Optional;

    @Repository  // this interface is responsible for data access
    public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {
        Page<CourseModule> findAllByUniversityId(Long universityId, Pageable pageable);

        Optional<CourseModule> findByIdAndUniversityId(Long id, Long universityId);

        Page<CourseModule> findAll(Pageable pageable);




        void deleteByIdAndUniversityId( Long universityId,Long id);


    }

