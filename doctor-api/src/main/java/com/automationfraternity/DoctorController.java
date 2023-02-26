package com.automationfraternity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("")
@Slf4j
public class DoctorController {

    private DoctorService doctorService;
    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    @PostMapping("/doctors")
    public Doctor createDoctor(@RequestBody Doctor doctor){
        return doctorService.createDoctor(doctor);
    }

    @PutMapping("/doctors")
    public Doctor updateDoctor(@RequestBody Doctor doctor) throws Exception {
        return doctorService.updateDoctor(doctor);
    }

    @DeleteMapping("/doctors/{registrationID}")
    public void deleteDoctorWithRegistrationID(@PathVariable String registrationID) throws Exception {
        doctorService.deleteDoctorByRegistrationID(registrationID);
    }

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }

    @GetMapping("/doctors/searchByEmail")
    public List<Doctor> getDoctorListByEmail(@RequestParam String email){
        return doctorService.getDoctorListByEmail(email);
    }

    @GetMapping("/doctors/{registrationID}")
    public Optional<Doctor> getDoctorListByRegistrationID(@PathVariable String registrationID){
        return doctorService.getDoctorListByRegistrationID(registrationID);
    }

    @PostMapping("/init_db")
    public void initDB(){
        Doctor doctor = Doctor.builder().withName("Dr Corsin").withIsDoctorAvailable(true).withEmailID("corsin@corsin.com").withCanDoHomeVisit(true).withClinicNameAndAddress("any").withExperienceInYears(20).withQualifications("MD").withSpecialization("Ortho").withRegistrationID("MR1234").build();
        Doctor doctor1 = Doctor.builder().withName("Dr Carola").withIsDoctorAvailable(true).withEmailID("carola@carola.com").withCanDoHomeVisit(true).withClinicNameAndAddress("any").withExperienceInYears(30).withQualifications("MBBS").withSpecialization("Gyn-OBS").withRegistrationID("MR3456").build();
        Doctor doctor2 = Doctor.builder().withName("Dr Jamil").withIsDoctorAvailable(false).withEmailID("jamil@jamil.com").withCanDoHomeVisit(true).withClinicNameAndAddress("any").withExperienceInYears(15).withQualifications("MBBS").withSpecialization("Neuro").withRegistrationID("MR8967").build();
        createDoctor(doctor);
        createDoctor(doctor1);
        createDoctor(doctor2);
    }

}
