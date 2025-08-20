package com.steps;
import com.hooks.Hooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SauceSteps {

    private WebDriver driver() { return Hooks.getDriver(); }

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutInfoPage infoPage;
    private CheckoutOverviewPage overviewPage;
    private CheckoutCompletePage completePage;

    @Given("I am on the SauceDemo login page")
    public void openLogin() {
        loginPage     = new LoginPage(driver());
        inventoryPage = new InventoryPage(driver());
        cartPage      = new CartPage(driver());
        infoPage      = new CheckoutInfoPage(driver());
        overviewPage  = new CheckoutOverviewPage(driver());
        completePage  = new CheckoutCompletePage(driver());

        loginPage.open();
    }

    @When("I login with valid credentials")
    public void loginValid() {
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(inventoryPage.isAt(), "Not on Products page after login");
    }

    @Then("I should land on the products page")
    public void onProducts() { assertTrue(inventoryPage.isAt(), "Not on Products page"); }

    @When("I sort products by {string}")
    public void sortBy(String visible) { inventoryPage.sortByVisibleText(visible); }

    @Then("product prices should be in ascending order")
    public void pricesAscending() {
        List<Double> actual = inventoryPage.getAllPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);
        assertEquals(expected, actual, "Prices are not ascending");
    }

    @When("I add the first {int} products to the cart")
    public void addFirstN(int n) {
        inventoryPage.addFirstNProducts(n);
        inventoryPage.goToCart();
        assertTrue(cartPage.isAtCart(), "Not on Cart page");
    }

    @When("I proceed to checkout")
    public void proceedCheckout() { cartPage.checkout(); }

    @When("I enter checkout info {string} {string} {string}")
    public void enterInfo(String f, String l, String z) { infoPage.enterInfo(f, l, z); }

    @When("I continue to overview and finish")
    public void continueAndFinish() {
        infoPage.clickContinue();
        assertTrue(overviewPage.isAtOverview(), "Not at Overview");
        overviewPage.finish();
    }

    @Then("I should see the order confirmation message")
    public void seeConfirmation() {
        String msg = completePage.confirmationText();
        assertNotNull(msg);
        assertTrue(msg.toLowerCase().contains("thank you"));
    }

    @When("I continue without entering checkout info")
    public void continueWithoutInfo() { infoPage.clickContinue(); }

    @Then("I should see an error {string}")
    public void seeError(String expected) { assertEquals(expected.trim(), infoPage.getError().trim()); }
}
