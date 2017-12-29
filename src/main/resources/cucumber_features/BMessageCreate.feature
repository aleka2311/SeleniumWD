# new feature
# Tags: optional
    
Feature: Create new Message
    
@smokeTest
Scenario Outline: Creating and Sending an Email Message
Given user creates new draft "<sender>" and "<subject>" and "<body>"
When user sends new draft "<sender>" and "<subject>" and "<body>"
Then check in sent draft "<sender>" and "<subject>" and "<body>"

Examples:
|sender      |subject   |body          |
|arai_23.11@mail.ru|my_subject|it's my letter|