package com.automationfraternity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/")
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
@CrossOrigin //All origins are allowed. Not a secure way though
public class PatientRestController {

    @Autowired
    PatientService patientService;

    @PostMapping("/patient")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Patient createPatient(@RequestBody Patient patient){
        return patientService.createNewPatient(patient);
    }

    @GetMapping("/patient")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Patient> getPatient(){
        return patientService.getPatient();
    }

    @GetMapping("/patient/{email}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<Patient> getPatientByEmail(@RequestParam String email){
        return patientService.getPatient(email);
    }

    @PutMapping("/patient")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Patient updatePatient(@RequestBody Patient patient) throws Exception {
        return patientService.updatePatient(patient);
    }

    @DeleteMapping("/patient")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public void deletePatient(@RequestParam String email){
        patientService.deletePatient(email);
    }

}
