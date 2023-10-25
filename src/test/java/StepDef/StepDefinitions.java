package StepDef;

import Locators.LoginPageLocators;
import Locators.NewSavingAccountLocators;
import Utils.Config;
import Utils.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StepDefinitions {


    Driver driver = new Driver();
    LoginPageLocators loginPage = new LoginPageLocators(driver.getDriver());
    NewSavingAccountLocators savingPage = new NewSavingAccountLocators(driver.getDriver());
    Actions action;
    Select select;



    @Given("User Navigates to TNB Login Page")
    public void user_navigates_to_tnb_login_page() {

        driver.getDriver().get(Config.getProperty("url"));
    }

    @When("User Enters Username as {string}")
    public void user_enters_username_as(String username) {

        loginPage.EnterUsername(username);
    }

    @When("User Enters Password as {string}")
    public void user_enters_password_as(String password) {

        loginPage.EnterPassword(password);
    }

    @And("User Clicks Login Button")
    public void userClicksLoginButton() {
        loginPage.ClickLoginButton();
    }

    @Then("User Should Successfully Login")
    public void userShouldSuccessfullyLogin() {
        Assert.assertEquals(driver.getDriver().getTitle(), "Bank Home Page");
        driver.closeDriver();
    }

    @Given("User On TNB Home Page")
    public void userOnTNBHomePage() {

        driver.getDriver().get(Config.getProperty("url"));
        loginPage.EnterUsername(Config.getProperty("username"));
        loginPage.EnterPassword(Config.getProperty("password"));
        loginPage.ClickLoginButton();
    }

    @When("User Hover Overs Open New Account")
    public void userHoverOversOpenNewAccount() {
        action = new Actions(driver.getDriver());
        action.moveToElement(savingPage.NewAccountButton).perform();
    }

    @And("User Clicks The New Saving Account")
    public void userClicksTheNewSavingAccount() {
        savingPage.SaveAccountButton.click();
    }

    @Then("User Should be Redirected to Business Account Page in New Window")
    public void userShouldBeRedirectedToBusinessAccountPageInNewWindow() {

        Set<String> windowIds = driver.getDriver().getWindowHandles();
        List<String> id = new ArrayList<>(windowIds);

        driver.getDriver().switchTo().window(id.get(1));
        Assert.assertTrue(savingPage.newBusinessAccountTitle.isDisplayed());
    }

    @When("User Enters Firstname and Lastname")
    public void userEntersFirstnameAndLastname() {

        savingPage.firstName.sendKeys(Config.getProperty("first_name"));
        savingPage.lastName.sendKeys(Config.getProperty("last_name"));
    }

    @And("User Enters Email and Business Name")
    public void userEntersEmailAndBusinessName() {

        savingPage.email.sendKeys(Config.getProperty("email"));
        savingPage.businessName.sendKeys(Config.getProperty("business_name"));
    }

    @And("User Enters Business Type and Business Address")
    public void userEntersBusinessTypeAndBusinessAddress() {

        savingPage.businessType.sendKeys(Config.getProperty("business_type"));
        savingPage.businessAddress.sendKeys(Config.getProperty("business_address"));
    }

    @And("User Enters Tax Id and Selected Business State")
    public void userEntersTaxIdAndSelectedBusinessState() {

        savingPage.taxId.sendKeys(Config.getProperty("tax_ID"));
        select = new Select(savingPage.stateBusiness);
        select.selectByVisibleText(Config.getProperty("business_state"));
    }

    @When("User Clicks the Submitted Button")
    public void userClicksTheSubmittedButton() {
        savingPage.submitButton.click();
    }

    @Then("User should See Success Message on alert and Accepts")
    public void userShouldSeeSuccessMessageOnAlertAndAccepts() {
        Alert alert = driver.getDriver().switchTo().alert();
        String message = alert.getText();
        Assert.assertEquals(message, Config.getProperty("message"));
        alert.accept();
    }

    @Then("User Should Be Redirected to Home Page")
    public void userShouldBeRedirectedToHomePage() {

        Set<String> windowIds = driver.getDriver().getWindowHandles();
        List<String> id = new ArrayList<>(windowIds);

        driver.getDriver().switchTo().window(id.get(0));
        Assert.assertEquals(driver.getDriver().getTitle(), "Bank Home Page");
        driver.closeDriver();
    }
}
