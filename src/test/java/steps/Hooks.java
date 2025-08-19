package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Hooks {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();
    private Path tempProfileDir;

    public static WebDriver getDriver() {
        return TL.get();
    }

    @Before
    public void setUp() throws Exception {
        // --- API precheck (requirement #3) ---
        HttpClient http = HttpClient.newHttpClient();
        HttpResponse<Void> res = http.send(
                HttpRequest.newBuilder(URI.create("https://www.saucedemo.com/")).GET().build(),
                HttpResponse.BodyHandlers.discarding()
        );
        if (res.statusCode() != 200) {
            throw new IllegalStateException("Precheck failed: GET / -> " + res.statusCode());
        }

        WebDriverManager.chromedriver().setup();

        tempProfileDir = Files.createTempDirectory("sd-chrome-profile");

        ChromeOptions opts = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        prefs.put("autofill.credit_card_enabled", false);
        opts.setExperimentalOption("prefs", prefs);

        opts.addArguments(
                "--disable-features=PasswordLeakDetection,PasswordManagerOnboarding",
                "--disable-save-password-bubble",
                "--disable-notifications",
                "--no-default-browser-check",
                "--user-data-dir=" + tempProfileDir.toAbsolutePath(),
                "--incognito" // extra isolation
        );
        opts.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        opts.setExperimentalOption("useAutomationExtension", false);

        if (System.getProperty("headless", "false").equalsIgnoreCase("true")) {
            opts.addArguments("--headless=new", "--window-size=1366,768");
        } else {
            opts.addArguments("--start-maximized");
        }

        TL.set(new ChromeDriver(opts));
        TL.get().manage().deleteAllCookies();
    }

    @After
    public void tearDown() {
        try {
            if (TL.get() != null) {
                TL.get().quit();
                TL.remove();
            }
        } finally {
            if (tempProfileDir != null) {
                try {
                    Files.walk(tempProfileDir)
                            .sorted((a, b) -> b.compareTo(a))
                            .forEach(p -> {
                                try {
                                    Files.deleteIfExists(p);
                                } catch (Exception ignored) {
                                }
                            });
                } catch (Exception ignored) {
                }
            }
        }
    }
}
