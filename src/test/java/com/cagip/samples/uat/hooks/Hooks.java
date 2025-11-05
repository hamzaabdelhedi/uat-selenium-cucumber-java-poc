package com.cagip.samples.uat.hooks;

import com.cagip.samples.uat.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Cucumber hooks for setup and teardown operations.
 * Manages WebDriver lifecycle across scenarios.
 */
public class Hooks {
    
    private WebDriver driver;
    
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        String browser = System.getProperty("browser", "chrome");
        String remoteBrowser = System.getProperty("remote.browser", "");
        String autoProvisioned = System.getProperty("auto.provisioned.browser", "false");
        
        System.out.println("Browser selected: " + browser);
        if (!remoteBrowser.isEmpty()) {
            System.out.println("Using remote browser at: " + remoteBrowser);
        }
        if (Boolean.parseBoolean(autoProvisioned)) {
            System.out.println("Using Docker-provisioned browser");
        }
        
        // Initialize driver through singleton
        driver = DriverFactory.getDriver();
    }
    
    @After
    public void tearDown(Scenario scenario) {
        // Take screenshot if scenario failed
        if (scenario.isFailed() && DriverFactory.isDriverInitialized()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot on failure");
        }
        
        System.out.println("Scenario finished: " + scenario.getName() + " - Status: " + scenario.getStatus());
        
        // Quit driver after all scenarios
        DriverFactory.quitDriver();
    }
}