package com.cagip.samples.uat.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Singleton pattern implementation for WebDriver management.
 * Ensures only one WebDriver instance is created per test execution.
 * Supports local, remote, and Docker-provisioned browsers.
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
     * Creates a new WebDriver instance based on configuration.
     * Supports local, remote, and Docker-provisioned browsers.
     * 
     * @return WebDriver instance
     */
    private static WebDriver createDriver() {
        String browser = System.getProperty("browser", DEFAULT_BROWSER).toLowerCase();
        String remoteBrowserUrl = System.getProperty("remote.browser", "");
        boolean autoProvisionedBrowser = Boolean.parseBoolean(
            System.getProperty("auto.provisioned.browser", "false")
        );
        
        WebDriver newDriver;
        
        // Check if using remote browser
        if (!remoteBrowserUrl.isEmpty()) {
            newDriver = createRemoteDriver(browser, remoteBrowserUrl);
        }
        // Check if using Docker-provisioned browser
        else if (autoProvisionedBrowser) {
            newDriver = createDockerDriver(browser);
        }
        // Default: local browser
        else {
            newDriver = createLocalDriver(browser);
        }
        
        // Configure timeouts
        newDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        newDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        
        return newDriver;
    }
    
    /**
     * Creates a local WebDriver instance.
     * 
     * @param browser Browser type (chrome, firefox, edge)
     * @return WebDriver instance
     */
    private static WebDriver createLocalDriver(String browser) {
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
        
        return newDriver;
    }
    
    /**
     * Creates a remote WebDriver instance using Selenium Grid or remote server.
     * 
     * @param browser Browser type (chrome, firefox, edge)
     * @param remoteUrl Remote browser URL
     * @return WebDriver instance
     */
    private static WebDriver createRemoteDriver(String browser, String remoteUrl) {
        try {
            URL url = new URL(remoteUrl);
            WebDriver newDriver;
            
            switch (browser) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-notifications");
                    newDriver = WebDriverManager.chromedriver()
                        .remoteAddress(url)
                        .capabilities(chromeOptions)
                        .create();
                    break;
                    
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--start-maximized");
                    newDriver = WebDriverManager.firefoxdriver()
                        .remoteAddress(url)
                        .capabilities(firefoxOptions)
                        .create();
                    break;
                    
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized");
                    newDriver = WebDriverManager.edgedriver()
                        .remoteAddress(url)
                        .capabilities(edgeOptions)
                        .create();
                    break;
                    
                default:
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }
            
            return newDriver;
            
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid remote browser URL: " + remoteUrl, e);
        }
    }
    
    /**
     * Creates a WebDriver instance with browser running in Docker container.
     * WebDriverManager will automatically download and manage the Docker image.
     * 
     * @param browser Browser type (chrome, firefox, edge)
     * @return WebDriver instance
     */
    private static WebDriver createDockerDriver(String browser) {
        WebDriver newDriver;
        
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                newDriver = WebDriverManager.chromedriver()
                    .browserInDocker()
                    .capabilities(chromeOptions)
                    .create();
                break;
                
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                newDriver = WebDriverManager.firefoxdriver()
                    .browserInDocker()
                    .capabilities(firefoxOptions)
                    .create();
                break;
                
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                newDriver = WebDriverManager.edgedriver()
                    .browserInDocker()
                    .capabilities(edgeOptions)
                    .create();
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
        
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