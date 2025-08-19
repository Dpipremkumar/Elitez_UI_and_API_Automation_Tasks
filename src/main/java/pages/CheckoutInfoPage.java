package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.LocatorRepo;

public class CheckoutInfoPage extends BasePage {
    private final By firstName   = LocatorRepo.by("checkout.first");
    private final By lastName    = LocatorRepo.by("checkout.last");
    private final By postalCode  = LocatorRepo.by("checkout.zip");
    private final By continueBtn = LocatorRepo.by("checkout.continue");
    private final By errorMsg    = LocatorRepo.by("checkout.error");

    public CheckoutInfoPage(WebDriver driver) { super(driver); }

    public void enterInfo(String f, String l, String z) {
        type(firstName, f);
        type(lastName,  l);
        type(postalCode,z);
    }
    public void clickContinue() { click(continueBtn); }
    public String getError()    { return text(errorMsg); }
}
