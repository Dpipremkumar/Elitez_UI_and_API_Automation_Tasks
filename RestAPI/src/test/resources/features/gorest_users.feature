@api
Feature: GoREST Users API

  Scenario: Create a new user, fetch it, then update it
    Given I have a valid GoRest access token
    When I create a new user
    Then the user is created successfully

    When I fetch that user by id
    Then the user details are returned

    When I update that user's details
    Then the user details are updated successfully
