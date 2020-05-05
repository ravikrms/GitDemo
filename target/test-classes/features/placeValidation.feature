#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Validating Place APIs

  @AddPlace @Regression
  Scenario Outline: Verify if the place is added succesfully using AddAPI
    Given Add Place Payload with "<name>""<language>""<address>"
    When user calls "AddPlaceAPI" using "POST" http request
    Then the API call is success with status code 200
    And "status" in ResponseBody is "OK"
    And "scope" in ResponseBody is "APP"
    And Verify place_id created maps to "<name>" using "GetPlaceAPI"
    
    Examples:
      |name   |language |address|
      |AAhouse|English  |World cross center|
 #     |BBhouse|Spanish  |World Trade center|
 @DeletePlace
    Scenario: Verify if Delete Place functionality is working
    
    Given Delete Place payload
    When user calls "DeletePlaceAPI" using "POST" http request
    Then the API call is success with status code 200
    And "status" in ResponseBody is "OK"



  
