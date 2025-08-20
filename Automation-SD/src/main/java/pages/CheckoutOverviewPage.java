package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.LocatorRepo;

public class CheckoutOverviewPage extends BasePage {
    private final By overviewTitle = LocatorRepo.by("overview.title");
    private final By finishBtn     = LocatorRepo.by("overview.finish");

    public CheckoutOverviewPage(WebDriver driver) { super(driver); }

    public boolean isAtOverview() { return displayed(overviewTitle); }
    public void finish() { click(finishBtn); }
}
