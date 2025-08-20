package utils;

import org.openqa.selenium.By;

import java.io.InputStream;
import java.util.Properties;

public class LocatorRepo {
    private static final Properties P = new Properties();

    static {
        try (InputStream in = LocatorRepo.class.getClassLoader().getResourceAsStream("locators.properties")) {
            if (in == null) throw new RuntimeException("locators.properties not found on classpath");
            P.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load locators.properties", e);
        }
    }

    public static By by(String key) {
        String v = P.getProperty(key);
        if (v == null) throw new IllegalArgumentException("Locator not found: " + key);

        if (v.startsWith("xpath=")) return By.xpath(v.substring(6));
        if (v.startsWith("css="))   return By.cssSelector(v.substring(4));
        if (v.startsWith("id="))    return By.id(v.substring(3));
        if (v.startsWith("name="))  return By.name(v.substring(5));

        // default fallback: treat as css
        return By.cssSelector(v);
    }
}
