Feature: Student Registration feature

  Scenario: Student Registration
  	Given an unregistered student with empty id
  	When the student is being registered
  	Then the student is registered
  	And has a non empty id  	