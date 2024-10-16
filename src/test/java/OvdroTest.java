import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class OvdroTest {

    @Test
    public void testAutomationToolsFormRegistrationValidFields() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

        String firstNameField = "//*[@id=\"firstName\"]";
        String lastNameField = "//*[@id=\"lastName\"]";
        String userEmailField = "//*[@id=\"userEmail\"]";
        String genderRadiobutton = "//*[@id=\"gender-radio-1\"]";
        String mobileNumberField = "//*[@id=\"userNumber\"]";
        String mobileNumber = "1234567890";
        String submitButton = "//*[@id=\"submit\"]";
        String closeButton = "//*[@id=\"closeLargeModal\"]";
        // String closeButtonText = "class org.openqa.selenium.remote.RemoteWebElement";


        String firstName = "FirstName";
        String lastName = "LastName";
        String email = "FirstNameLastName@test.test";

        driver.findElement(By.xpath(firstNameField)).sendKeys(firstName);
        driver.findElement(By.xpath(lastNameField)).sendKeys(lastName);
        driver.findElement(By.xpath(userEmailField)).sendKeys(email);

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath(genderRadiobutton))).click().perform();

        driver.findElement(By.xpath(mobileNumberField)).sendKeys(mobileNumber);
        actions.moveToElement(driver.findElement(By.xpath(submitButton))).click().perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement closeModalButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(closeButton)));
        Assert.assertTrue(closeModalButton.isDisplayed(), "true");

        closeModalButton.click();

        driver.quit();

    }


}
