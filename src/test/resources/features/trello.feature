Feature: Trello

  Scenario: POST Create a new board
    When I send POST request to "https://api.trello.com/1/boards" with body
    """
    {
    "name": "POST: Create new board for testing proposed"
    }
    """
    Then Response status code should be 200
    Then I store response as "responseBoardPOST"
    And Response should contain data
      | name | POST: Create new board for testing proposed |
    Then I send DELETE request to "https://api.trello.com/1/boards/{responseBoardPOST.id}"


   Scenario: PUT A board
     When I send POST request to "https://api.trello.com/1/boards" with body
    """
    {
    "name": "PUT: Create new board for testing proposed"
    }
    """
     Then Response status code should be 200
     Then I store response as "responseBoardPUT"
     Then I send a PUT request to "https://api.trello.com/1/boards/{responseBoardPUT.id}" with body
    """
    {
    "name": "PUT: Updating this board"
    }
    """
     Then Response status code should be 200
      And I send DELETE request to "https://api.trello.com/1/boards/{responseBoardPUT.id}"


  Scenario: GET A board
    When I send POST request to "https://api.trello.com/1/boards" with body
    """
    {
    "name": "GET: Create new board for testing proposed"
    }
    """
    Then Response status code should be 200
    Then I store response as "responseBoardGET"
    When I send a GET request to "https://api.trello.com/1/boards/{responseBoardGET.id}"
     And Response should contain data
      | name | GET: Create new board for testing proposed |
    Then Response status code should be 200
    Then I send DELETE request to "https://api.trello.com/1/boards/{responseBoardGET.id}"

   @createBoard
   Scenario: DELETE Board
   When I send DELETE request to "https://api.trello.com/1/boards/{responseBoard.id}"