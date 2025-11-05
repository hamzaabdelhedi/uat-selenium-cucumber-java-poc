package com.cagip.samples.uat.steps;

import com.cagip.samples.uat.driver.DriverFactory;
import com.cagip.samples.uat.pages.LoggedInSuccessfullyPage;
import com.cagip.samples.uat.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step definitions for Login feature scenarios.
 */
public class LoginStepDefinitions {
    
    private WebDriver driver;
    private LoginPage loginPage;
    private LoggedInSuccessfullyPage loggedInPage;
    
    public LoginStepDefinitions() {
        this.driver = DriverFactory.getDriver();
        this.loginPage = new LoginPage(driver);
        this.loggedInPage = new LoggedInSuccessfullyPage(driver);
    }
    
    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage.navigateToLoginPage();
    }
    
    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
    }
    
    @When("I enter password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }
    
    @When("I click the submit button")
    public void iClickTheSubmitButton() {
        loginPage.clickSubmit();
    }
    
    @When("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
        loginPage.login(username, password);
    }
    
    @Then("I should be redirected to the success page")
    public void iShouldBeRedirectedToTheSuccessPage() {
        assertTrue(loggedInPage.urlContains("logged-in-successfully"), 
                "URL does not contain 'logged-in-successfully'");
    }
    
    @Then("the page should contain {string}")
    public void thePageShouldContain(String expectedText) {
        assertTrue(loggedInPage.pageContainsText(expectedText), 
                "Page does not contain expected text: " + expectedText);
    }
    
    @Then("the logout button should be displayed")
    public void theLogoutButtonShouldBeDisplayed() {
        assertTrue(loggedInPage.isLogoutButtonDisplayed(), 
                "Logout button is not displayed");
    }
    
    @Then("an error message should be displayed")
    public void anErrorMessageShouldBeDisplayed() {
        assertTrue(loginPage.isErrorMessageDisplayed(), 
                "Error message is not displayed");
    }
    
    @Then("the error message should say {string}")
    public void theErrorMessageShouldSay(String expectedErrorMessage) {
        String actualErrorMessage = loginPage.getErrorMessageText();
        assertEquals(expectedErrorMessage, actualErrorMessage, 
                "Error message does not match");
    }
}