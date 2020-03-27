Feature: Boards

  @createBoardPreCond @deleteBoardPostCond
  Scenario: Request a single board
    When I send GET request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200
      And response body should match with "src/test/resources/jacky/getSchema.json" json schema

  @deleteBoardPostCond
  Scenario: Post a single board
    When I send a POST request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200 
      And response body should match with "src/test/resources/jacky/postSchema.json" json schema

  @createBoardPreCond
  Scenario: Delete an existent single board
    When I send a DELETE request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200
    And response body should match with "src/test/resources/jacky/deleteSchema.json" json schema

  @createBoardPreCond @deleteBoardPostCond
  Scenario: Update an existent single board
    When I send a PUT request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200
    And response body should match with "src/test/resources/jacky/putSchema.json" json schema
