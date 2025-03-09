Feature: REST API Testing
  As an API consumer
  I want to verify API endpoints functionality
  So that I can ensure the API is working correctly

  @api @smoke @regression
  Scenario: Get user by ID
    Given I have a valid API token
    When I send a GET request to "/api/users/1"
    Then the response status code should be 200
    And the response should contain user details with id "1"
    And the response time should be less than 1000 milliseconds

  @api @regression
  Scenario: Create a new user
    Given I have a valid API token
    And I have user data with name "John Doe" and email "john.doe@example.com"
    When I send a POST request to "/api/users"
    Then the response status code should be 201
    And the response should contain the user id
    And the response should contain user details with name "John Doe"

  @api @regression
  Scenario: Update an existing user
    Given I have a valid API token
    And I have user data with updated name "John Smith"
    When I send a PUT request to "/api/users/1"
    Then the response status code should be 200
    And the response should contain user details with name "John Smith"

  @api @regression
  Scenario: Delete a user
    Given I have a valid API token
    When I send a DELETE request to "/api/users/1"
    Then the response status code should be 204
    And when I send a GET request to "/api/users/1"
    Then the response status code should be 404
    And the response should contain error message "User not found"

  @api @regression
  Scenario Outline: Get users with query parameters
    Given I have a valid API token
    When I send a GET request to "/api/users" with query parameter "page" as "<page>" and "limit" as "<limit>"
    Then the response status code should be 200
    And the response should contain "<count>" users
    And the response should have pagination information

    Examples:
      | page | limit | count |
      | 1    | 10    | 10    |
      | 2    | 5     | 5     |
      | 3    | 2     | 2     |