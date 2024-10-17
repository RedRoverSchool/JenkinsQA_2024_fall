import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static java.lang.Thread.sleep;

public class AnnaL {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void testOpenRegisterPage(){
        driver.get("https://www.sharelane.com/cgi-bin/main.py");

        WebElement singUpButton = driver.findElement(By.linkText("Sign up"));
        singUpButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.sharelane.com/cgi-bin/register.py");
    }

    @Test
    public void testSendInvalid4DigitsZipCode() throws InterruptedException {
        sendZipCode("1234");

        WebElement errorMessage = driver.findElement(By.xpath("//span[@class='error_message']"));

        Assert.assertEquals(errorMessage.getText(), "Oops, error on page. ZIP code should have 5 digits");
    }

    @Test
    public void testSendValid5digitsZipCode() throws InterruptedException {
        sendZipCode("12345");

        List elements = driver.findElements(By.xpath("//tr/td/p[text()!='Sign Up']"));

        Assert.assertEquals(elements.size(), 5);
    }

    @Test
    public void testRegister() throws InterruptedException {
        sendZipCode("11111");
        sleep(2000);
        register("A", "B", "1@mail.ru", "1111", "1111");

        sleep(2000);

        WebElement confirmationMessage = driver.findElement(By.xpath("//span[@class='confirmation_message']"));
        Assert.assertEquals("Account is created!", confirmationMessage.getText());
    }

    public void sendZipCode(String zipCode) throws InterruptedException {
        testOpenRegisterPage();

        WebElement inputZipCode = driver.findElement(By.name("zip_code"));
        inputZipCode.sendKeys(zipCode);

        sleep(2000);

        WebElement buttonContinue = driver.findElement(By.xpath("//input[@value='Continue']"));
        buttonContinue.click();
    }

    public void register(String firstName, String lastName, String email, String password, String confirmPassword){

        WebElement inputFirstName = driver.findElement(By.xpath("//input[@name='first_name']"));
        WebElement inputLastName = driver.findElement(By.xpath("//input[@name='last_name']"));
        WebElement inputEmail = driver.findElement(By.xpath("//input[@name='email']"));
        WebElement inputPassword = driver.findElement(By.xpath("//input[@name='password1']"));
        WebElement inputConfirmPassword = driver.findElement(By.xpath("//input[@name='password2']"));
        WebElement buttonRegister = driver.findElement(By.xpath("//input[@type='submit' and @value='Register']"));

        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
        inputConfirmPassword.sendKeys(confirmPassword);
        buttonRegister.click();
    }

}
