import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import static java.lang.Thread.sleep;

public class PavelKhalilovTest {

    @Test
    public void testTransitionByUrl() throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://thinking-tester-contact-list.herokuapp.com");
        sleep(300);

        Assert.assertEquals(driver.getCurrentUrl(), "https://thinking-tester-contact-list.herokuapp.com/");
        Assert.assertEquals(driver.getTitle(), "Contact List App");

        WebElement pageHeading = driver.findElement(By.xpath("//h1['Contact List App']"));
        Assert.assertEquals(pageHeading.getText(), "Contact List App");

        driver.quit();
    }

    @Test
    public void testSignUpButton() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
        driver.manage().window().maximize();
        sleep(300);

        WebElement signUpButton = driver.findElement(By.xpath("//button[@id='signup']"));
        signUpButton.click();
        sleep(300);

        Assert.assertEquals(driver.getCurrentUrl(), "https://thinking-tester-contact-list.herokuapp.com/addUser");
        Assert.assertEquals(driver.getTitle(), "Add User");

        WebElement pageHeading = driver.findElement(By.xpath("//h1['Add User']"));
        Assert.assertEquals(pageHeading.getText(), "Add User");

        driver.quit();
    }

    @Test
    public void testRegistrationWithValidCredentials() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://thinking-tester-contact-list.herokuapp.com/addUser");
        driver.manage().deleteAllCookies();

        WebElement firstName = driver.findElement(By.xpath("//input[@id='firstName']"));
        firstName.sendKeys("Pavel");

        WebElement lastName = driver.findElement(By.xpath("//input[@id='lastName']"));
        lastName.sendKeys("Khalilov");

        WebElement email = driver.findElement(By.id("email"));
        //!!!!!!!change number to next in email before each test
        email.sendKeys("my2email@gmail.com");

        WebElement password = driver.findElement(By.xpath("//input[@placeholder='Password']"));
        password.sendKeys("1234567890");

        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        sleep(300);
        Assert.assertEquals(driver.getCurrentUrl(), "https://thinking-tester-contact-list.herokuapp.com/contactList");
        Assert.assertEquals(driver.getTitle(), "My Contacts");

        WebElement headingPage = driver.findElement(By.tagName("h1"));
        Assert.assertEquals(headingPage.getText(), "Contact List");

    }

}
