package com.automationfraternity.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder(setterPrefix = "with",toBuilder = true)
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;
    String age;
    String email;
    String phone;
    String medicalConditions;
}
