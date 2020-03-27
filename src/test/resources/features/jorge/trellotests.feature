Feature: Boards

  @createBoardPreCondition @deleteBoardPostCondition
  Scenario: Board would be retrieved
    When I send GET request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200
      And Response body should match with "src/test/resources/schemas/jorge/getSchema.json" json schema

  @deleteBoardPostCondition
  Scenario: Board would be created
    When I send POST request to "https://api.trello.com/1/boards/" with body "BoardName"
    Then Response status code should be 200
    And Response body should match with "src/test/resources/schemas/jorge/postSchema.json" json schema

  @createBoardPreCondition @deleteBoardPostCondition
  Scenario: Board name would be updated
    When I send PUT request to "https://api.trello.com/1/boards/" name "BoardNameUpdated"
    Then Response status code should be 200
    And Response body should match with "src/test/resources/schemas/jorge/putSchema.json" json schema

  @createBoardPreCondition
  Scenario: Board would be deleted
    When I send DELETE request to "https://api.trello.com/1/boards/"
    Then Response status code should be 200
    And Response body should match with "src/test/resources/schemas/jorge/deleteSchema.json" json schema
