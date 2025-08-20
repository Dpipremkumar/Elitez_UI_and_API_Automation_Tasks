package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.LocatorRepo;

public class CheckoutCompletePage extends BasePage {
    private final By completeHeader = LocatorRepo.by("complete.header");

    public CheckoutCompletePage(WebDriver driver) { super(driver); }

    public String confirmationText() { return text(completeHeader); }
}
