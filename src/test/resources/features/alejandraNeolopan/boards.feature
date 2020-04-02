Feature: Boards

  @cleanBoardPostCond
  Scenario: Post Boards creates a new board
    When POST boards is executed
      | Content-Type | application/json |
      | name | New%2CBoard |
      | defaultLabels | true |
      | defaultLists | true |
      | desc | Created%20by%20API |
      | keepFromSource | none |
      | prefs_permissionLevel | private |
      | prefs_voting | disabled |
      | prefs_comments | members |
      | prefs_invitations | members |
      | prefs_selfJoin | true |
      | prefs_cardCovers | true |
      | prefs_background | blue |
      | prefs_cardAging | regular |
    Then a new board data is retrieved

  @cleanBoardPostCond
  Scenario: Get Boards by ID
    Given a board has been created
    When GET boards is executed
    Then board data is retrieved

  @cleanBoardPostCond
  Scenario: Put Boards updates a board
    Given a board has been created
    When PUT boards is executed with changes
      | name | Updated%20name |
      | desc | By%20APICode |
    Then board data is retrieved with updated data

  Scenario: Delete Boards removes a board
    Given a board has been created
    When DELETE boards is executed
    Then 200 status is retrieved
