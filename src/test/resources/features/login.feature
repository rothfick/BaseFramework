Feature: User Authentication
  As a user of the application
  I want to be able to login and logout
  So that I can access protected resources and secure my account

  @ui @smoke @regression
  Scenario: Successful login with valid credentials
    Given I navigate to the application
    When I enter username "testuser" in the login form
    And I enter password "password123" in the login form
    And I click the login button
    Then I should be logged in successfully
    And the dashboard page should be displayed
    And the welcome message should contain the username "testuser"

  @ui @regression
  Scenario Outline: Failed login with invalid credentials
    Given I navigate to the application
    When I enter username "<username>" in the login form
    And I enter password "<password>" in the login form
    And I click the login button
    Then I should see an error message "<error_message>"
    And I should remain on the login page

    Examples:
      | username  | password    | error_message                   |
      | testuser  | wrongpass   | Invalid username or password    |
      | wronguser | password123 | Invalid username or password    |
      |           | password123 | Username is required            |
      | testuser  |             | Password is required            |
      |           |             | Username and password required  |

  @ui @regression
  Scenario: Logout from the application
    Given I am logged in with username "testuser" and password "password123"
    When I click the logout button
    Then I should be logged out successfully
    And I should be redirected to the login page