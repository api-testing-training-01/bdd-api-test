Feature: Projects

  @createProjectPreCond @deleteProjectPreCond2
  Scenario: All projects can be retrieved
    When I send GET request to "https://www.pivotaltracker.com/services/v5/projects"
    Then Response status code should be 200
      And Response body should match with "src/test/resources/schemas/getProjects.json" json schema

  @deleteCreatedProject
  Scenario: Create new project
    When I send POST request to "https://www.pivotaltracker.com/services/v5/projects" with body
    """
    {
     "name":"Executioner1",
     "public": true,
     "iteration_length": 2
    }
    """
    Then Response status code should be 200
      And Response body should match with "src/test/resources/schemas/postSchema.json" json schema
      And Response should contain the following data
        | name             | Executioner1 |
        | public           | false        |
        | iteration_length | 2            |

  @deleteCreatedProject
  Scenario: Delete project
    Given I send POST request to "https://www.pivotaltracker.com/services/v5/projects" with body
    """
    {"name":"Executioner1"}
    """
      And I store response as "PostResponse"
    Then Response status code should be 200
    When I send DELETE request to "https://www.pivotaltracker.com/services/v5/projects/{PostResponse.id}" with body
      Then Response status code should be 204
