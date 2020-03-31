Feature: Boards

  @deleteBoardPostCondition
  Scenario: Board would be retrieved
    Given I send POST request to "https://api.trello.com/1/boards/" with following data
      | name  | BoardPOSTName           |
      | desc  | This is my description  |
      Then I store response as "TrelloResponse"
      And Response status code should be 200
    When I send GET request to "https://api.trello.com/1/boards/{TrelloResponse.id}"
      Then Response body should match with "src/test/resources/schemas/jorge/getSchema.json" json schema
      And Response should contain the following data
        |name| BoardPOSTName|
        |desc| This is my description |

  @deleteBoardPostCondition
  Scenario: Board would be created
    When I send POST request to "https://api.trello.com/1/boards/" with following data
      | name  | BoardPOSTName           |
      | desc  | This is my description  |
    Then Response status code should be 200
      And Response body should match with "src/test/resources/schemas/jorge/postSchema.json" json schema
      And Response should contain the following data
      |name| BoardPOSTName|
      |desc| This is my description |

  @deleteBoardPostCondition
  Scenario: Board fields would be updated
    Given I send POST request to "https://api.trello.com/1/boards/" with following data
        | name  | BoardPutName           |
        | desc  | This is my description  |

    Then Response status code should be 200
      And I store response as "TrelloResponse" updating the following fields
        | name  | BoardPutNameModified           |
        | desc  | This is my description modified  |

    When I send PUT request to "https://api.trello.com/1/boards/{TrelloResponse.id}?name={TrelloResponse.name}&desc={TrelloResponse.desc}"
      Then Response status code should be 200
      And Response body should match with "src/test/resources/schemas/jorge/putSchema.json" json schema
      And Response should contain the following data
        |name| BoardPutNameModified|
        |desc| This is my description modified |

  Scenario: Board would be deleted
    Given I send POST request to "https://api.trello.com/1/boards/" with following data
      | name  | BoardPutName           |
      | desc  | This is my description  |
      Then I store response as "TrelloResponse"
      And Response status code should be 200
    When I send DELETE request to "https://api.trello.com/1/boards/{TrelloResponse.id}"
      Then Response status code should be 200
      And Response body should match with "src/test/resources/schemas/jorge/deleteSchema.json" json schema
