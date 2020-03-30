Feature: CRUD Trello Project
  Scenario: Create a board
    Given I want a board with a 'BDDTest' as a 'name'
    And I want a board with a 'This is a BDD test' as a 'desc'
    When I send 'POST' to Trello API with this information
    Then Response status code should be 200
    And Response 'POST' body should match with 'POST' schema
    And Response should contain the following data
      |name|BDDTest|
      |desc|This is a BDD test|

  @createDefaultBoard
  Scenario: Get board by id
    Given I have a board id
    When I send 'GET' to Trello API with this information
    Then Response status code should be 200
    And Response 'GET' body should match with 'GET' schema
    And Response should contain the following data
      |name|ApiTestingDefault|
      |desc|Learning to use Cucumber|

  @createDefaultBoard
  Scenario: Updated board by id
    Given I have a board id
    And I want a board with a 'BDDTestUpdate' as a 'name'
    And I want a board with a 'This is a Update BDD test' as a 'desc'
    When I send 'PUT' to Trello API with this information
    Then Response status code should be 200
    And Response 'PUT' body should match with 'PUT' schema
    And Response should contain the following data
      |name|BDDTestUpdate|
      |desc|This is a Update BDD test|

  @createDefaultBoard
  Scenario: Delete board by id
    Given I have a board id
    When I send 'DELETE' to Trello API with this information
    Then Response status code should be 200
    And Response 'DELETE' body should match with 'DELETE' schema
