package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import service.CheckExchangeRatesService;

public class CheckExchangeRatesStepDefs extends CheckExchangeRatesService {

    @Given("User gets exchange rates")
    public void userGetsExchangeRates() {
        checkExchangeRates();
    }

    @Then("User prints exchange rate for currency code: {string}")
    public void userDisplaysExchangeRateForCurrencyCode(String code) {
        checkExchangeRatesByCode(code);
    }

    @And("User prints exchange rate for currency name: {string}")
    public void userDisplaysExchangeRateForCurrencyName(String name) {
        checkExchangeRatesByName(name);
    }

    @And("User prints exchange rates above {float}")
    public void userDisplaysExchangeRatesAbove(float abovePrice) {
        checkExchangeRatesAbovePrice(abovePrice);
    }

    @And("User prints exchange rates below {float}")
    public void userDisplaysExchangeRatesBelow(float belowPrice) {
        checkExchangeRatesBelowPrice(belowPrice);
    }
}
