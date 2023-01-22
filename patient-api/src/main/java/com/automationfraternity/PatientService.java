package com.automationfraternity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    public Patient createNewPatient(Patient patient){
        return patientRepository.save(patient);
    }

    public List<Patient> getPatient() {
        return patientRepository.findAll();
    }

    public List<Patient> getPatient(String email) {
        return patientRepository.findByEmail(email);
    }

    public Patient updatePatient(Patient patient) throws Exception {
        if (getPatient(patient.getEmail()).isEmpty()){
            throw new Exception("Email Id not found. Can not update. " + patient.toString());
        }
        return patientRepository.save(patient);
    }

    public void deletePatient(String email) {
        patientRepository.deleteByEmail(email);
    }
}
