@sauceDemo
Feature: SauceDemo quality checks (POM + OR + API precheck)

  Background:
    Given I am on the SauceDemo login page

  @login
  Scenario: Successful login
    When I login with valid credentials
    Then I should land on the products page

  @sort
  Scenario: Sort by Price (low to high)
    When I login with valid credentials
    And I sort products by "Price (low to high)"
    Then product prices should be in ascending order

  @checkout
  Scenario: Complete checkout with two items
    When I login with valid credentials
    And I add the first 2 products to the cart
    And I proceed to checkout
    And I enter checkout info "User" "one" "600001"
    And I continue to overview and finish
    Then I should see the order confirmation message

  @negative
  Scenario: Checkout validation error when info missing
    When I login with valid credentials
    And I add the first 1 products to the cart
    And I proceed to checkout
    And I continue without entering checkout info
    Then I should see an error "Error: First Name is required"
