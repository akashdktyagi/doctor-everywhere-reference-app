package com.automationfraternity.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@ToString
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class DoctorEntity {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String registrationID;
    @Column(nullable = false)
    String qualifications;
    @Column(nullable = false)
    String specialization;
    @Column(nullable = false)
    Integer experienceInYears;
    @Column(nullable = false)
    String emailID;
    @Column(nullable = false)
    String clinicNameAndAddress;
    @Column(nullable = false)
    Boolean canDoHomeVisit;
    @Column(nullable = false)
    Boolean isDoctorAvailable;



}
