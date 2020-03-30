Feature: Boards

  @deleteBoardPostCond
  Scenario: Request a single board
    Given I send a POST request to "https://api.trello.com/1/boards/"
    And I store response as "response"
    Then response status code should be 200
    When I send GET request to "https://api.trello.com/1/boards/{response.id}"
    Then response status code should be 200
    And response body should match with "src/test/resources/jacky/getSchema.json" json schema

  @deleteBoardPostCond
  Scenario: Post a single board
    When I send a POST request to "https://api.trello.com/1/boards/" with body
    """
    {
    "name": "New Board IntellIJ",
    "desc": "new board created from API testing"
    }
    """
    Then response status code should be 200
    And response body should match with "src/test/resources/jacky/postSchema.json" json schema
    And response should contain the following data
        | name     | New Board IntellIJ |
        | desc     | new board created from API testing |

  Scenario: Delete a single board
    Given I send a POST request to "https://api.trello.com/1/boards/"
    And I store response as "response"
    Then response status code should be 200
    When I send a DELETE request to "https://api.trello.com/1/boards/{response.id}"
    Then response status code should be 200
    And  response body should match with "src/test/resources/jacky/deleteSchema.json" json schema


  @createBoardPreCond @deleteBoardPostCond
  Scenario: Update an existent single board
    Given I send a POST request to "https://api.trello.com/1/boards/"
    And I store response as "response"
    Then response status code should be 200
    When I send a PUT request to "https://api.trello.com/1/boards/{response.id}" with body
    """
    {
    "name": "Updated Board IntellIJ",
    "desc": "Updated board from API testing"
    }
    """
    Then response status code should be 200
    And response should contain the following data
      | name     | Updated Board IntellIJ |
      | desc     | Updated board from API testing |
