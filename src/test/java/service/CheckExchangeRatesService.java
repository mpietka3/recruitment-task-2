package service;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.List;
import java.util.Map;

public class CheckExchangeRatesService {
    public final static String BASE_URL = "https://api.nbp.pl/";
    public final static String GET_EXCHANGE_RATES_URL = "api/exchangerates/tables/A";
    private static Response response;
    private static List<Map<String, Object>> rates;

    public void checkExchangeRates() {
        response = getExchangeRates();
        Reporter.log("Status code: " + response.getStatusCode(), true);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Can not get exchange rates");
        rates = getRates(response);
    }

    public void checkExchangeRatesByCode(String code) {
        Map<String, Object> exchangeRateByCode = rates.stream()
                .filter(rate -> code.equals(rate.get("code")))
                .findFirst()
                .orElse(null);

        if (exchangeRateByCode != null) {
            Reporter.log("Exchange rate by code " + code + ": " + exchangeRateByCode.get("mid"), true);
        } else {
            Reporter.log("Exchange rate for code " + code + " not found.", true);
        }
    }

    public void checkExchangeRatesByName(String name) {
        Map<String, Object> exchangeRateByName = rates.stream()
                .filter(rate -> name.equalsIgnoreCase((String) rate.get("currency")))
                .findFirst()
                .orElse(null);

        if (exchangeRateByName != null) {
            Reporter.log("Exchange rate by name " + name + ": " + exchangeRateByName.get("mid"), true);
        } else {
            Reporter.log("Exchange rate for code " + name + " not found.", true);
        }
    }

    public void checkExchangeRatesAbovePrice(float abovePrice) {
        List<Map<String, Object>> exchangeRatesAbovePrice = rates.stream()
                .filter(rate -> (float) rate.get("mid") > abovePrice)
                .toList();

        if (exchangeRatesAbovePrice.size() != 0) {
            Reporter.log("Exchange rates above " + abovePrice + ":", true);
            exchangeRatesAbovePrice.forEach(rate -> Reporter.log(rate.get("code") + " : " + rate.get("mid"), true));
        } else {
            Reporter.log("No currencies found above requested price", true);
        }
    }

    public void checkExchangeRatesBelowPrice(float belowPrice) {
        List<Map<String, Object>> exchangeRatesBelowPrice = rates.stream()
                .filter(rate -> (float) rate.get("mid") < belowPrice)
                .toList();

        if (exchangeRatesBelowPrice.size() != 0) {
            Reporter.log("Exchange rates below " + belowPrice + ":", true);
            exchangeRatesBelowPrice.forEach(rate -> Reporter.log(rate.get("code") + " : " + rate.get("mid"), true));
        } else {
            Reporter.log("No currencies found below requested price", true);
        }
    }

    private Response getExchangeRates() {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .when()
                .get(GET_EXCHANGE_RATES_URL)
                .then()
                .extract().response();
    }

    private List<Map<String, Object>> getRates(Response response) {
        List<Map<String, Object>> body = response.jsonPath().getList("");
        rates = (List<Map<String, Object>>) body.get(0).get("rates");

        return rates;
    }
}
