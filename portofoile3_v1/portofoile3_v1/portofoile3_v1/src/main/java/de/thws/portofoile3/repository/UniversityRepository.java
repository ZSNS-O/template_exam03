package de.thws.portofoile3.repository;

import de.thws.portofoile3.model.University;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import org.springframework.data.domain.Pageable;

import java.util.List;


@Repository       // this interface is responsible for data access
public interface UniversityRepository extends JpaRepository<University,Long> {






    Page<University> findByNameContainingIgnoreCase(String name, Pageable pageable);

}


