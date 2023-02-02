@issue:GCRMC-2236
Feature: As a User, I want to mange Patient Details for a Health App
  So that I can create, edit, fetch and delete patient details.

  @xrayTest:GCRMC-2243
  Scenario: As a client, I am able to create a new patient
    Given I have patient details as below
      | name              | akash                  |
      | age               | 37                     |
      | email             | akash@akash.com        |
      | phone             | 123456789              |
      | medicalConditions | i am just plain stupid |
    When I create the patient
    Then a new patient is created

  @xrayTest:GCRMC-2244
  Scenario: As a client, I am able to get patient details by email
    Given I have patient with email as "a@a.com"
    When I get the patient with email as "a@a.com"
    Then patient details with email as "a@a.com" is returned

  @xrayTest:GCRMC-2245
  Scenario: As a client, I am able to update the patient details by email
    Given I have patient with email as "a@a.com"
    When I update the patient with email as "a@a.com" with new name "newName"
    Then patient with email as "a@a.com" is updated with new name as "newName"

  @xrayTest:GCRMC-2246
  Scenario: As a client, I am able to delete the patient with email
    Given I have patient with email as "a@a.com"
    When I delete the patient with email as "a@a.com"
    Then patient with email as "a@a.com" is deleted