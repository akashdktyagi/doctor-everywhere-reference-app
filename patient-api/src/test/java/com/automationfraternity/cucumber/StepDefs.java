package com.automationfraternity.cucumber;

import com.automationfraternity.Patient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URI;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StepDefs {

    String server = "http://localhost:9096";
    String postEndPoint = "/patient";
    String getEndPoint = "/patient";
    String putEndPoint = "/patient";
    String deleteEndPoint = "/patient";
    String body = "";
    ResultActions resultsAction;

    @Autowired
    MockMvc mockMvc;

    Patient patient;

    @Given("I have patient details as below")
    public void i_have_patient_details_as_below(Map<String,String> data) throws JsonProcessingException {
        String name = data.get("name");
        String age = data.get("age");
        String email = data.get("email");
        String phone = data.get("phone");
        String medicalConditions = data.get("medicalConditions");

        patient = Patient.builder()
                .withName(name)
                .withAge(age)
                .withEmail(email)
                .withPhone(phone)
                .withMedicalConditions(medicalConditions)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        body = objectMapper.writeValueAsString(patient);

    }

    @When("I create the patient")
    public void i_create_the_patient() throws Exception {
        resultsAction = mockMvc.perform(
                            post(URI.create(server + postEndPoint))
                            .contentType("application/json")
                            .content(body)
                        );
    }

    @Then("a new patient is created")
    public void a_new_patient_is_created() throws Exception {
        resultsAction.andExpect(status().is(201));
        resultsAction.andExpect(content().string("{\"id\":1,\"name\":\"akash\",\"age\":\"37\",\"email\":\"akash@akash.com\",\"phone\":\"123456789\",\"medicalConditions\":\"i am just plain stupid\"}"));
    }

    @Given("I have patient with email as {string}")
    public void i_have_patient_with_email_as(String email) throws Exception {
        patient = Patient.builder()
                .withName("name")
                .withAge("12")
                .withEmail(email)
                .withPhone("12345")
                .withMedicalConditions("fever")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        body = objectMapper.writeValueAsString(patient);
        mockMvc.perform(
                post(URI.create(server + postEndPoint))
                        .contentType("application/json")
                        .content(body)

        );
    }

    @When("I get the patient with email as {string}")
    public void i_get_the_patient_with_email_as(String email) throws Exception {
        resultsAction = mockMvc.perform(
                get(URI.create(server + getEndPoint +"?email="+email))
                        .contentType("application/json")
                        .content(body)
        );
    }

    @Then("patient details with email as {string} is returned")
    public void patient_details_with_email_as_is_returned(String email) throws Exception {
        resultsAction.andExpect(status().is(200));
        String responseBody = resultsAction.andReturn().getResponse().getContentAsString();
        Assertions.assertThat(responseBody).containsSequence(email);

    }

    @When("I update the patient with email as {string} with new name {string}")
    public void i_update_the_patient_with_email_as_with_new_name(String email, String newName) throws Exception {
        Patient patientWithNewName = patient.toBuilder().withName(newName).build();
        ObjectMapper objectMapper = new ObjectMapper();
        body = objectMapper.writeValueAsString(patientWithNewName);
        resultsAction = mockMvc.perform(
                put(URI.create(server + putEndPoint+"?email="+email))
                        .contentType("application/json")
                        .content(body));
    }
    @Then("patient with email as {string} is updated with new name as {string}")
    public void patient_with_email_as_is_updated_with_new_name_as(String oldEmail, String newName) throws Exception {
        resultsAction.andExpect(status().is(201));
        String responseBody = resultsAction.andReturn().getResponse().getContentAsString();
        Assertions.assertThat(responseBody).containsSequence(newName);
    }

    @When("I delete the patient with email as {string}")
    public void i_delete_the_patient_with_email_as(String email) throws Exception {
        resultsAction = mockMvc.perform(
                delete(URI.create(server + deleteEndPoint+"?email="+email)));
    }

    @Then("patient with email as {string} is deleted")
    public void patient_with_email_as_is_deleted(String email) throws Exception {
        resultsAction.andExpect(status().is(200));
        i_get_the_patient_with_email_as(email);
        String responseString = resultsAction.andReturn().getResponse().getContentAsString();
        Assertions.assertThat(responseString).doesNotContain(email);
    }
}
