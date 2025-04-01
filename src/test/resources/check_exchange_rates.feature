Feature: Check exchange rates
  Funcionality gets current average exchange rates and print information about requested one

  Scenario: Get current average exchange rates and print information about requested one
    Given User gets exchange rates
    Then User prints exchange rate for currency code: "USD"
    And User prints exchange rate for currency name: "dolar amerykański"
    And User prints exchange rates above 5
    And User prints exchange rates below 3
