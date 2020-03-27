Feature: Task 6 about CRUD of Board - Trello Tests

  @deleteBoardTrello
  Scenario: Create a Board in Trello with POST request
    When I send POST request to "https://api.trello.com/1/boards"
    Then Response status code should be 200
    And Response body should match with "src/test/resources/schemas/postBoardSchema.json" json schema

  @createBoardTrello
  Scenario: Delete a Board of Trello with DELETE request
    When I send DELETE request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200
    And Response body should match with "src/test/resources/schemas/deleteBoardSchema.json" json schema

  @createBoardTrello @deleteBoardTrello
  Scenario: Retrieve a Board of Trello with GET request
    When I send GET request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200
    And Response body should match with "src/test/resources/schemas/getBoardSchema.json" json schema

  @createBoardTrello @deleteBoardTrello
  Scenario: Update a Board of Trello with PUT request
    When I send PUT request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200
    And Response body should match with "src/test/resources/schemas/putBoardSchema.json" json schema