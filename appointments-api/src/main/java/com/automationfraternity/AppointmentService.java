package com.automationfraternity;

import com.automationfraternity.exceptions.DoctorNotFoundException;
import com.automationfraternity.exceptions.PatientNotFoundException;
import com.automationfraternity.model.AppointmentEntity;
import com.automationfraternity.model.DoctorEntity;
import com.automationfraternity.model.PatientEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class AppointmentService {

    AppointmentRepository repository;

    @Value("${externals.apis.patient-api.host}")
    String patientApiHost;

    @Value("${externals.apis.patient-api.port}")
    String patientApiPort;

    @Value("${externals.apis.doctor-api.host}")
    String doctorApiHost;

    @Value("${externals.apis.doctor-api.port}")
    String doctorApiPort;

    @Autowired
    RestTemplate restTemplate;

    public String getMyPetWithStatusAs(String status){
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity("/v2/pet/findByStatus?status="+status,Object.class);
        return String.valueOf(responseEntity.getBody());
    }
    @Autowired
    public AppointmentService(AppointmentRepository repository){
        this.repository = repository;
    }

    public AppointmentEntity createNewAppointment(AppointmentEntity entity) throws PatientNotFoundException, DoctorNotFoundException {

        //Check if PatientEntity exist in the system
        boolean patientExist = isPatientExist(entity.getPatientEmail());
         if (!patientExist){
            throw new PatientNotFoundException("Patient with email as : " + entity.getPatientEmail() + " does not exist in the system. Please register this Patient First.");
         }

        //Check if DoctorEntity exist in the System
        boolean doctorExist = isDoctorExist(entity.getDoctorEmail());
        if (!doctorExist){
            throw new DoctorNotFoundException("Doctor with email as : " + entity.getDoctorEmail() + " does not exist in the system. Please register this Doctor First.");
        }

        //Check if PatientEntity has any existing appointment during this time frame

        //Check if DoctorEntity  has any existing appointment during this time frame


        return repository.save(entity);
    }

    public List<AppointmentEntity> getAllAppointment() {
        return repository.findAll();
    }

    public boolean isPatientExist(String email){
        String url = patientApiHost+":"+patientApiPort+"/patient/searchByEmail?email="+email;
        ParameterizedTypeReference <List<PatientEntity>> parameterizedTypeReference = new ParameterizedTypeReference<List<PatientEntity>>(){};
        ResponseEntity<List<PatientEntity>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,null,parameterizedTypeReference);
        log.debug("Patient exist check result: " + !responseEntity.getBody().isEmpty());
        return !responseEntity.getBody().isEmpty();
    }

    public boolean isDoctorExist(String email){
        String url = doctorApiHost+":"+doctorApiPort+"/doctors/searchByEmail?email="+email;
        ParameterizedTypeReference <List<DoctorEntity>> parameterizedTypeReference = new ParameterizedTypeReference<List<DoctorEntity>>(){};
        ResponseEntity<List<DoctorEntity>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,null,parameterizedTypeReference);
        log.debug("Doctor exist check result: " + !responseEntity.getBody().isEmpty());
        return !responseEntity.getBody().isEmpty();
    }

}
