Feature: Boards

  @cleanBoardPostCond
  Scenario: Post Boards creates a new board
    When POST "/1/boards/" is executed
      | Content-Type          | application/json   |
      | name                  | New%20Board        |
      | defaultLabels         | true               |
      | defaultLists          | true               |
      | desc                  | Created%20by%20API |
      | keepFromSource        | none               |
      | prefs_permissionLevel | private            |
      | prefs_voting          | disabled           |
      | prefs_comments        | members            |
      | prefs_invitations     | members            |
      | prefs_selfJoin        | true               |
      | prefs_cardCovers      | true               |
      | prefs_background      | blue               |
      | prefs_cardAging       | regular            |
    Then 200 status is retrieved
    And schema should match with "src/test/resources/schemas/alejandraNeolopan/postSchema.json"
    And board data is retrieved
      | name | New%20Board        |
      | desc | Created%20by%20API |


  @cleanBoardPostCond
  Scenario: Get Boards by ID
    Given a board has been created as "NewBoard"
      | name | Default |
      | desc | Default |
    When GET "/1/boards/{NewBoard.id}" is executed
    Then 200 status is retrieved
    And schema should match with "src/test/resources/schemas/alejandraNeolopan/getSchema.json"
    And board data is retrieved
      | desc | Default |

  @cleanBoardPostCond
  Scenario: Put Boards updates a board
    Given a board has been created as "NewBoard"
      | name | Default |
      | desc | Default |
    When PUT "/1/boards/{NewBoard.id}" is executed with changes
      | name | Updated%20name |
      | desc | By%20APICode   |
    Then 200 status is retrieved
    And schema should match with "src/test/resources/schemas/alejandraNeolopan/putSchema.json"
    And board data is retrieved
      | name | Updated%20name |
      | desc | By%20APICode   |

  Scenario: Delete Boards removes a board
    Given a board has been created as "NewBoard"
      | name | Default |
      | desc | Default |
    When DELETE "/1/boards/{NewBoard.id}" is executed
    Then 200 status is retrieved
    And schema should match with "src/test/resources/schemas/alejandraNeolopan/deleteSchema.json"

