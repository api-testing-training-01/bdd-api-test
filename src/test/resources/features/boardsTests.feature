Feature: Boards

  @getToken @deleteBoard
  Scenario: create new Board
    When I send POST request to "/1/boards/" to create a board with body
    """
    {
    "name": "BOARD 01 GP",
    "desc": "Created From Automation Testing"
    }
    """
    And Setting the "Board_ID"
    Then Status code should be 200
    And Response should match with "src/test/resources/schemas/postBoardSchema.json" json schema
    And Response should contain data
      | name   | BOARD 01 GP                     |
      | desc   | Created From Automation Testing |
      | closed | false                           |

  @getToken @deleteBoard
  Scenario: update Board
    Given I send POST request to "/1/boards/" to create a board with body
    """
    {
    "name": "BOARD 01 GP",
    "desc": "Created From Automation Testing"
    }
    """
    And Setting the "Board_ID"
    Then Status code should be 200
    When I send PUT request to "/1/boards/{Board_ID.id}/" to update "GP Board updated" name
    Then Status code should be 200
    And Response should match with "src/test/resources/schemas/putBoardSchema.json" json schema
    And Response should contain data
      | name   | GP Board updated                |
      | desc   | Created From Automation Testing |
      | closed | false                           |

  @getToken @deleteBoard
  Scenario: get Board
    Given I send POST request to "/1/boards/" to create a board with body
    """
    {
    "name": "BOARD 01 GP",
    "desc": "Created From Automation Testing"
    }
    """
    And Setting the "Board_ID"
    Then Status code should be 200
    When I send GET request to "/1/boards/{Board_ID.id}/" body board
    Then Status code should be 200
    And Response should match with "src/test/resources/schemas/getBoardSchema.json" json schema


  @getToken
  Scenario: delete Board
    Given I send POST request to "/1/boards/" to create a board with body
    """
    {
    "name": "BOARD 01 GP",
    "desc": "Created From Automation Testing"
    }
    """
    And Setting the "Board_ID"
    Then Status code should be 200
    When I send Delete request to "/1/boards/{Board_ID.id}/" to delete a board already created
    Then Status code should be 200
    And Response should match with "src/test/resources/schemas/deleteBoardSchema.json" json schema