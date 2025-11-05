package com.cagip.samples.uat.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the Logged In Successfully Page.
 * This page appears after successful login.
 */
public class LoggedInSuccessfullyPage extends BasePage {
    
    @FindBy(css = ".post-title")
    private WebElement pageTitle;
    
    @FindBy(css = ".post-content p")
    private WebElement successMessage;
    
    @FindBy(linkText = "Log out")
    private WebElement logoutButton;
    
    public LoggedInSuccessfullyPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Gets the success message text.
     * 
     * @return Success message text
     */
    public String getSuccessMessageText() {
        waitForElementToBeVisible(successMessage);
        return successMessage.getText();
    }
    
    /**
     * Checks if the page contains expected success text.
     * 
     * @param expectedText Text to check for (e.g., "Congratulations" or "successfully logged in")
     * @return true if text is found
     */
    public boolean pageContainsText(String expectedText) {
        String messageText = getSuccessMessageText();
        return messageText.contains(expectedText);
    }
    
    /**
     * Checks if logout button is displayed.
     * 
     * @return true if logout button is displayed
     */
    public boolean isLogoutButtonDisplayed() {
        try {
            waitForElementToBeVisible(logoutButton);
            return logoutButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Clicks the logout button.
     */
    public void clickLogout() {
        clickElement(logoutButton);
    }
    
    /**
     * Verifies if URL contains the expected path.
     * 
     * @param expectedPath Expected path in URL
     * @return true if URL contains the path
     */
    public boolean urlContains(String expectedPath) {
        return getCurrentUrl().contains(expectedPath);
    }
}