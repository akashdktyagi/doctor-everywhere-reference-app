package com.automationfraternity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JpaRepository is a JPA (Java Persistence API) specific extension of Repository.
 * It contains the full API of CrudRepository and PagingAndSortingRepository.
 * T: Domain type that repository manages (Generally the Entity/Model class name)
 * ID: Type of the id of the entity that repository manages (Generally the wrapper class of your @Id that is created inside the Entity/Model class)
 */
public interface PatientRepository extends JpaRepository<Patient,Long> {
    List<Patient> findByEmail(String email);
    void deleteByEmail(String email);
}
