Feature: Trello project
  @deleteBoard
  Scenario: POST create a board
    When I send POST request to "https://api.trello.com/1/boards"
    Then Response status code should be 200
    And Response body should match with "src\\test\\resources\\schemas\\postSchema.json" json schema

  @createBoard
  Scenario: Delete a created board
    When I send DELETE request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200
    And Response body should match with "src\\test\\resources\\schemas\\deleteSchema.json" json schema

  @createBoard @deleteBoard
  Scenario: GET  a board
    When I send GET request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200
    And Response body should match with "src\\test\\resources\\schemas\\getSchema.json" json schema

  @createBoard @deleteBoard
  Scenario: PUT a board
    When I send PUT request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200
    And Response body should match with "src\\test\\resources\\schemas\\putSchema.json" json schema