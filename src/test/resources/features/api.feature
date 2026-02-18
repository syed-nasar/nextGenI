Feature: API Automation Tests
  API test scenarios for Login, Create Agency, and Get Agency by ID

  Scenario: Case 1 - Login with invalid credentials returns error message
    Given I send a POST request to login with email "admin@gmail.com" and password "123456"
    Then the response status code should be 401 or 400
    And the response message should be "Invalid credentials"

  Scenario: Case 2 - Create agency without authentication returns error message
    Given I send a POST request to create agency with name "Syed Nasar Ahmed" address "PECHS Block 6" phone "03417392647" email "nasar.syed2@gmail.com"
    Then the response status code should be 401
    And the response message should be "Please authenticate"

  Scenario: Case 3 - Login create agency and validate by get by id
    Given I login with valid credentials email "admin@gmail.com" and password "admin@123"
    And I store the access token from login response
    When I send a POST request to create agency with Bearer token with unique email
    And I store the agency id from create response
    And I send a GET request to get agency by stored id
    Then the response agency data should match the created agency payload
