Feature: get functionality

  Scenario Outline: As a customer I want to say hello.
    Given I can send a new hello
    And I sending get to be say hello with hello id <hello_id>
    Then I should be able to see my newly created hello
    Examples:
      | hello_id |
      | 12       |


  Scenario Outline: As a customer I want to register myself in DB.
    Given I can send a new request with username and password
    And I sending post to register with username <user_name> and password <pass_word>
    Then I should be able to get response as ResponseEntity.ok
    Examples:
      | user_name | pass_word |
      | abc      | abcd |



