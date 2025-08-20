package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LocatorRepo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage {

    private final By productsTitle = LocatorRepo.by("inventory.title");               // "Products"
    private final By inventoryContainer = By.id("inventory_container");                    // stable root
    private final By inventoryItems = By.cssSelector(".inventory_list .inventory_item");
    private final By priceLabels = LocatorRepo.by("inventory.priceLabels");

    private final By addButtons = LocatorRepo.by("inventory.addButtons");
    private final By cartLink = LocatorRepo.by("inventory.cartLink");

    private final By sortByDataTest = By.cssSelector("[data-test='product_sort_container']");
    private final By sortByClass = By.cssSelector("select.product_sort_container");
    private final By sortByXPath = By.xpath("//select[@data-test='product_sort_container' or contains(@class,'product_sort_container')]");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAt() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(25))
                    .until(ExpectedConditions.urlContains("inventory.html"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(productsTitle));
            wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryContainer));
            new WebDriverWait(driver, Duration.ofSeconds(25))
                    .until(d -> d.findElements(inventoryItems).size() > 0);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private WebElement locateSortSelect() {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(20));
        By[] locs = new By[]{sortByDataTest, sortByClass, sortByXPath};
        for (By by : locs) {
            try {
                WebElement el = w.until(ExpectedConditions.presenceOfElementLocated(by));
                scrollIntoView(el);
                w.until(ExpectedConditions.visibilityOf(el));
                w.until(ExpectedConditions.elementToBeClickable(el));
                return el;
            } catch (TimeoutException ignored) {

            }
        }
        throw new TimeoutException("Sort <select> not found by any locator");
    }

    public void sortByVisibleText(String visibleText) {
        if (!isAt()) throw new TimeoutException("Inventory page not ready");

        final String targetValue = "lohi";

        for (int attempt = 1; attempt <= 2; attempt++) {
            try {
                WebElement selectEl = locateSortSelect();


                boolean done = false;
                try {
                    new Select(selectEl).selectByVisibleText(visibleText);
                    done = true;
                } catch (Exception ignored) {

                }

                if (!done) {
                    String js =
                            "const sel=arguments[0], val=arguments[1];" +
                                    "sel.value = val;" +
                                    "sel.dispatchEvent(new Event('change', {bubbles:true}));";
                    ((JavascriptExecutor) driver).executeScript(js, selectEl, targetValue);
                }

                waitUntilPricesAscending();
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException | TimeoutException e) {
                if (attempt == 2) throw e;
            }
        }
    }

    private void waitUntilPricesAscending() {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
        w.until(d -> {
            List<Double> prices = getAllPrices();
            if (prices.size() < 2) return false;
            for (int i = 1; i < prices.size(); i++) {
                if (prices.get(i) < prices.get(i - 1)) return false;
            }
            return true;
        });
    }

    public List<Double> getAllPrices() {
        List<Double> out = new ArrayList<>();
        for (WebElement e : driver.findElements(priceLabels)) {
            out.add(Double.parseDouble(e.getText().replace("$", "").trim()));
        }
        return out;
    }

    public void addFirstNProducts(int n) {
        List<WebElement> btns = driver.findElements(addButtons);
        for (int i = 0; i < Math.min(n, btns.size()); i++) btns.get(i).click();
    }

    public void goToCart() {
        click(cartLink);
    }
}
