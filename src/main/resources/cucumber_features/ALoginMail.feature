# new feature
# Tags: optional
    
Feature: ProtonMail

@smokeTest
Scenario: ProtonLogin
Given user navigates to proton home page
When  click login button
And   enters user credentials and submits login form
Then  proton inbox page is displayed
