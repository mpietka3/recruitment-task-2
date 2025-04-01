Feature: Check exchange rates
  Funcionality gets current average exchange rates and display information about requested one

  Scenario: Get current average exchange rates and display information about requested one
    Given User gets exchange rates
    Then User displays exchange rate for currency code: "USD"
    And User displays exchange rate for currency name: "dolar amerykański"
    And User displays exchange rates above 5
    And User displays exchange rates below 3
