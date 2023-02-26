package com.automationfraternity;

import com.automationfraternity.exceptions.DoctorNotFoundException;
import com.automationfraternity.exceptions.PatientNotFoundException;
import com.automationfraternity.model.AppointmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class AppointmentController {

    AppointmentService service;

    @Autowired
    public AppointmentController(AppointmentService service){
        this.service = service;
    }

    @PostMapping("/appointment")
    public AppointmentEntity createAppointment(@RequestBody AppointmentEntity entity) throws PatientNotFoundException, DoctorNotFoundException {
        return service.createNewAppointment(entity);
    }

    @GetMapping("/appointment")
    public List<AppointmentEntity> getAppointment(){
        return service.getAllAppointment();
    }

    @PutMapping("/appointment")
    public void editAppointment(){

    }

    @DeleteMapping("/appointment")
    public void deleteAppointment(){

    }

}
