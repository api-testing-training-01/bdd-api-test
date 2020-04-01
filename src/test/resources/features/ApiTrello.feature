Feature: Trello project
  @deleteBoard
  Scenario: POST create a board
    When I send POST request to "https://api.trello.com/1/boards" with name
    """
    FV - Trello board
    """
    Then Response status code should be 200
    And Response should contain data
      | name | FV - Trello board |

  @createBoard
  Scenario: Delete a created board
    When I send DELETE request to "https://api.trello.com/1/boards/{PostResponse.id}"
    Then Response status code should be 200

  @createBoard @deleteBoard
  Scenario: GET  a board
    When I send GET request to "https://api.trello.com/1/boards/{PostResponse.id}"
    Then Response status code should be 200
    And Response should contain data
      | name | FV - Trello board |

  @createBoard @deleteBoard
  Scenario: PUT a board
    When I send PUT request to "https://api.trello.com/1/boards/{PostResponse.id}" to desc
    """
    update was successful
    """
    Then Response status code should be 200
    And Response should contain data
      | desc     | update was successful |