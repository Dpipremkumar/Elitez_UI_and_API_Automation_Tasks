package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.LocatorRepo;

public class CartPage extends BasePage {
    private final By cartTitle   = LocatorRepo.by("cart.title");
    private final By checkoutBtn = LocatorRepo.by("cart.checkout");

    public CartPage(WebDriver driver) { super(driver); }

    public boolean isAtCart() { return displayed(cartTitle); }
    public void checkout() { click(checkoutBtn); }
}
