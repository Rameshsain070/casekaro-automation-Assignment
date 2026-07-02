Feature: Casekaro Add to Cart Automation

  Scenario: Search, select, and validate mobile covers in cart
    Given I navigate to the Casekaro website
    When I click on "Mobile Covers" from the Top Navigation Menu
    And I search for "Apple" in the phone cases by model search box
    Then I validate that other brands are not visible in the search results
    When I search for "iPhone 16 Pro" and select the exact match from the dropdown
    And I click "Choose Options" on the First Product Card
    And I add the Hard, Soft, and Glass material variants to the cart
    And I open the cart
    Then I validate that all 3 items are added to the cart
    And I print the price, material, and link of all items in the console