Feature: Login Functionality
  As a user
  I want to be able to login to the application
  So that I can access my account

  Background:
    Given I am on the login page

  Scenario: Positive Login Test - Successful login with valid credentials
    When I enter username "student"
    And I enter password "Password123"
    And I click the submit button
    Then I should be redirected to the success page
    And the page should contain "Congratulations"
    And the page should contain "successfully logged in"
    And the logout button should be displayed

  Scenario: Negative Login Test - Invalid username
    When I enter username "incorrectUser"
    And I enter password "Password123"
    And I click the submit button
    Then an error message should be displayed
    And the error message should say "Your username is invalid!"

  Scenario: Negative Login Test - Invalid password
    When I enter username "student"
    And I enter password "incorrectPassword"
    And I click the submit button
    Then an error message should be displayed
    And the error message should say "Your password is invalid!"

  Scenario Outline: Multiple invalid login attempts
    When I login with username "<username>" and password "<password>"
    Then an error message should be displayed
    And the error message should say "<errorMessage>"

    Examples:
      | username      | password          | errorMessage                 |
      | wrongUser     | Password123       | Your username is invalid!    |
      | student       | wrongPassword     | Your password is invalid!    |
      | invalidUser   | invalidPassword   | Your username is invalid!    |