package de.thws.portfolio3.repository;

import de.thws.portfolio3.model.University;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


import org.springframework.data.domain.Pageable;

import java.util.List;


@Repository       // this interface is responsible for data access
public interface UniversityRepository extends JpaRepository<University,Long> {






    Page<University> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<University> findByNameContainingIgnoreCase(String name, Sort sort);

}


