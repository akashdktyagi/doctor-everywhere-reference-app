package com.automationfraternity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByEmailID(String emailID);
    Optional<Doctor> findByRegistrationID(String registrationID);
    void deleteByRegistrationID(String registrationID);
}
