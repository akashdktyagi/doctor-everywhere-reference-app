package com.automationfraternity.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class AppointmentEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String bookingDateTime;
    String appointmentDateTime;
    String patientEmail;
    String doctorEmail;
    String clinicName;
}
