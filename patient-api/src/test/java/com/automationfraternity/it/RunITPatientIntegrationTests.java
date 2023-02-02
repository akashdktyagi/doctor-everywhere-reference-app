package com.automationfraternity.it;

import com.automationfraternity.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.net.URI;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * This Class is created to contrast Cucumber and Normal Integration Test.
 * Both are written the same way except Cucumber Test acts as abstraction layer on top of the tests
 */
@SpringBootTest
@AutoConfigureMockMvc
class RunITPatientIntegrationTests {

    String server = "http://localhost:8082";
    String postEndPoint = "/patient";
    String getEndPoint = "/patient";
    String putEndPoint = "/patient";
    String deleteEndPoint = "/patient";

    @Autowired
    MockMvc mockMvc;

    @Test
    public void test_create_a_new_patient() throws Exception {
        Patient patient = Patient.builder()
                .withName("akash")
                .withAge("37")
                .withEmail("akash@akash.com")
                .withPhone("123456789")
                .withMedicalConditions("i am just plain stupid")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(patient);

        ResultActions resultsAction = mockMvc.perform(
                post(URI.create(server + postEndPoint))
                        .contentType("application/json")
                        .content(body)
        );

        resultsAction.andExpect(status().is(201));
        resultsAction.andExpect(content().string("{\"id\":1,\"name\":\"akash\",\"age\":\"37\",\"email\":\"akash@akash.com\",\"phone\":\"123456789\",\"medicalConditions\":\"i am just plain stupid\"}"));
    }

    @Test
    @DirtiesContext
    public void test_get_patient_by_email() throws Exception {
        Patient patient = iHaveAPatientWithPatientEmailAs("a@a.com");

        ResultActions resultsAction = mockMvc.perform(
                get(URI.create(server + getEndPoint +"?email=a@a.com"))
        );

        resultsAction.andExpect(status().is(200));
        String responseBody = resultsAction.andReturn().getResponse().getContentAsString();
        Assertions.assertThat(responseBody).containsSequence("a@a.com");
    }

    @Test
    @DirtiesContext
    public void test_update_patient_detail_by_email() throws Exception {
        Patient patient = iHaveAPatientWithPatientEmailAs("a@a.com");
        Patient patientWithNewName = patient.toBuilder().withName("Prakash").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(patientWithNewName);
        ResultActions resultsAction = mockMvc.perform(
                put(URI.create(server + putEndPoint+"?email=a@a.com"))
                        .contentType("application/json")
                        .content(body));
        resultsAction.andExpect(status().is(201));
        String responseBody = resultsAction.andReturn().getResponse().getContentAsString();
        Assertions.assertThat(responseBody).containsSequence("Prakash");
    }


    /*
    Reusable method to create data
     */
    public Patient iHaveAPatientWithPatientEmailAs(String email) throws Exception {
        Patient patient = Patient.builder()
                .withName("name")
                .withAge("12")
                .withEmail(email)
                .withPhone("12345")
                .withMedicalConditions("fever")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(patient);
        mockMvc.perform(
                post(URI.create(server + postEndPoint))
                        .contentType("application/json")
                        .content(body)

        );
        return patient;
    }
}