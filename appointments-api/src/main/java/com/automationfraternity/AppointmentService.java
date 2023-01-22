package com.automationfraternity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    AppointmentRepository repository;

    @Autowired
    public AppointmentService(AppointmentRepository repository){
        this.repository = repository;
    }

    public AppointmentEntity createNewAppointment(AppointmentEntity entity) {
        return repository.save(entity);
    }

    public List<AppointmentEntity> getAllAppointment() {
        return repository.findAll();
    }
}
