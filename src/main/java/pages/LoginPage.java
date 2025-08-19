package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private static final String URL = "https://www.saucedemo.com/";

    private final By username = By.xpath("//input[@data-test='username' or @id='user-name']");
    private final By password = By.xpath("//input[@data-test='password' or @id='password']");
    private final By loginBtn = By.xpath("//*[@data-test='login-button']");
    private final By loginLogo = By.xpath("//div[contains(@class,'login_logo')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(URL);
        el(loginLogo);
    }

    public void login(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginBtn);
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }
}
