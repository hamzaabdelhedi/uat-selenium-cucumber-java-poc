package com.cagip.samples.uat.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Singleton pattern implementation for WebDriver management.
 * Ensures only one WebDriver instance is created per test execution.
 */
public class DriverFactory {
    
    private static WebDriver driver;
    private static final String DEFAULT_BROWSER = "chrome";
    
    private DriverFactory() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Gets the singleton WebDriver instance.
     * Creates it if it doesn't exist.
     * 
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }
    
    /**
     * Creates a new WebDriver instance based on the browser system property.
     * 
     * @return WebDriver instance
     */
    private static WebDriver createDriver() {
        String browser = System.getProperty("browser", DEFAULT_BROWSER).toLowerCase();
        
        WebDriver newDriver;
        
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                newDriver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                newDriver = new FirefoxDriver(firefoxOptions);
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                newDriver = new EdgeDriver(edgeOptions);
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        
        // Configure implicit wait
        newDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        newDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        
        return newDriver;
    }
    
    /**
     * Quits the WebDriver instance and sets it to null.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    
    /**
     * Checks if driver is initialized.
     * 
     * @return true if driver exists, false otherwise
     */
    public static boolean isDriverInitialized() {
        return driver != null;
    }
}