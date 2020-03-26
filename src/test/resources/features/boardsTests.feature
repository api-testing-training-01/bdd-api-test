Feature: Boards

  @deleteBoard
  Scenario: create new Board
    Given Valid token for "https://api.trello.com"
    When I send POST request to "/1/boards/" to create a board with "GP Board" name
    Then Status code should be 200
    And Response should match with "src/test/resources/schemas/postBoardSchema.json" json schema

  @createBoard @deleteBoard
  Scenario: update Board
    Given Valid token for "https://api.trello.com"
    When I send PUT request to "/1/boards/" to update "GP Board updated" name
    Then Status code should be 200
    And Response should match with "src/test/resources/schemas/putBoardSchema.json" json schema

  @createBoard @deleteBoard
  Scenario: get Board
    Given Valid token for "https://api.trello.com"
    When I send GET request to "/1/boards/" body board
    Then Status code should be 200
    And Response should match with "src/test/resources/schemas/getBoardSchema.json" json schema

  @createBoard
  Scenario: delete Board
    When I send Delete request to "/1/boards/" to delete a board already created
    Then Status code should be 200
    And Response should match with "src/test/resources/schemas/deleteBoardSchema.json" json schema