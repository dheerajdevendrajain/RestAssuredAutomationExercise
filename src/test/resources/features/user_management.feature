Feature: User Management API Tests

  Scenario: Create a new user with valid data
    Given I have a new user payload with name "Test User", email "test.user@example.com", gender "male" and status "active"
    When I send a POST request to the "/users" endpoint
    Then the response status code should be 201
    And the response body should match the JSON schema "schemas/user_schema.json"
    And the response body should contain "id"
    And the user "name" should be "Test User"
    And the user "email" should be "test.user@example.com"
    And the user "status" should be "active"
    And I save the created user ID for future use

  Scenario: Retrieve user by ID
    Given a user with ID "saved_user_id" exists
    When I send a GET request to the "/users/{id}" endpoint with ID "saved_user_id"
    Then the response status code should be 200
    And the response body should match the JSON schema "schemas/user_schema.json"
    And the user "id" should be "saved_user_id"
    And the user "name" should be "Test User"

  Scenario: Update an existing user
    Given a user with ID "saved_user_id" exists
    And I have an updated user payload for ID "saved_user_id" with status "inactive"
    When I send a PUT request to the "/users/{id}" endpoint with ID "saved_user_id"
    Then the response status code should be 200
    And the user "status" should be "inactive"

  Scenario: Delete a user
    Given a user with ID "saved_user_id" exists
    When I send a DELETE request to the "/users/{id}" endpoint with ID "saved_user_id"
    Then the response status code should be 204
    And I verify that the user with ID "saved_user_id" no longer exists