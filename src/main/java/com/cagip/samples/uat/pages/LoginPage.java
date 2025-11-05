package com.cagip.samples.uat.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the Login Page.
 * URL: https://practicetestautomation.com/practice-test-login/
 */
public class LoginPage extends BasePage {
    
    private static final String LOGIN_PAGE_URL = "https://practicetestautomation.com/practice-test-login/";
    
    @FindBy(id = "username")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "submit")
    private WebElement submitButton;
    
    @FindBy(id = "error")
    private WebElement errorMessage;
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Navigates to the login page.
     */
    public void navigateToLoginPage() {
        driver.get(LOGIN_PAGE_URL);
    }
    
    /**
     * Enters username into the username field.
     * 
     * @param username Username to enter
     */
    public void enterUsername(String username) {
        enterText(usernameField, username);
    }
    
    /**
     * Enters password into the password field.
     * 
     * @param password Password to enter
     */
    public void enterPassword(String password) {
        enterText(passwordField, password);
    }
    
    /**
     * Clicks the submit button.
     */
    public void clickSubmit() {
        clickElement(submitButton);
    }
    
    /**
     * Performs complete login operation.
     * 
     * @param username Username to login with
     * @param password Password to login with
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSubmit();
    }
    
    /**
     * Checks if error message is displayed.
     * 
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        try {
            waitForElementToBeVisible(errorMessage);
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Gets the error message text.
     * 
     * @return Error message text
     */
    public String getErrorMessageText() {
        waitForElementToBeVisible(errorMessage);
        return errorMessage.getText();
    }
}