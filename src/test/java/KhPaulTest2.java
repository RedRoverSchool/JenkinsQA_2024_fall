import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Random;

import static java.lang.Thread.sleep;

public class KhPaulTest2 {
    public String generateUniqueEmail() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // Генерируем случайное число от 0 до 9999
        return "JohnDoe" + randomNumber + "@mi.com";
    }
    WebDriver driver;

    @BeforeMethod
    public void SignUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get("https://thinking-tester-contact-list.herokuapp.com");

        WebElement signUpButton = driver.findElement(By.xpath("//button[@id='signup']"));
        signUpButton.click();
        sleep(1000);

        WebElement firstNameInput = driver.findElement(By.xpath("//input[@id = 'firstName']"));
        firstNameInput.sendKeys("Ubergez");

        WebElement lastName = driver.findElement(By.xpath("//*[@id='lastName']"));
        lastName.sendKeys("Odinson");

        WebElement eMail = driver.findElement(By.xpath("//*[@id='email']"));
        String uniqueEmail = generateUniqueEmail();
        eMail.sendKeys(uniqueEmail);

        WebElement password = driver.findElement(By.xpath("//*[@id='password']"));
        password.sendKeys("1234567");

        WebElement submit = driver.findElement(By.xpath("//*[@id='submit']"));
        submit.click();
        sleep(1000);
    }

    @Test
    public void TestContactList(){
        WebElement contactList = driver.findElement(By.xpath("//h1[text()='Contact List']"));
        Assert.assertEquals(contactList.getText(),"Contact List");
    }

    @Test
    public  void TestAddContactTitle() throws InterruptedException {
        WebElement addContactButton = driver.findElement(By.xpath("//button[@id ='add-contact']"));
        addContactButton.click();
        sleep(1000);

        WebElement AddContactList = driver.findElement(By.xpath("//h1[text()='Add Contact']"));
        Assert.assertEquals(AddContactList.getText(), "Add Contact");

    }
    @Test
    public void  TestAddContactError() throws  InterruptedException{
        WebElement addContactButton = driver.findElement(By.xpath("//button[@id ='add-contact']"));
        addContactButton.click();
        sleep(1000);

        WebElement firstNameInput = driver.findElement(By.xpath("//input[@id = 'firstName']"));
        firstNameInput.sendKeys("Ubergez");

        WebElement lastName = driver.findElement(By.xpath("//*[@id='lastName']"));
        lastName.sendKeys("Odinson");

        WebElement dateBirth = driver.findElement(By.xpath("//*[@id='birthdate']"));
        dateBirth.sendKeys("2000-12-12");

        WebElement eMail = driver.findElement(By.xpath("//*[@id='email']"));
        String uniqueEmail = generateUniqueEmail();
        eMail.sendKeys(uniqueEmail);

        WebElement phone = driver.findElement(By.xpath("//*[@id='phone']"));
        phone.sendKeys("456787654322");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='submit']"));
        submitButton.click();
        sleep(300);

        WebElement undefined = driver.findElement(By.xpath("//*[@id='error']"));
        Assert.assertEquals(undefined.getText(), "Contact validation failed: phone: Phone number is invalid");

    }
    @Test
    public void  TestAddContact() throws  InterruptedException{
        WebElement addContactButton = driver.findElement(By.xpath("//button[@id ='add-contact']"));
        addContactButton.click();
        sleep(1000);

        WebElement firstNameInput = driver.findElement(By.xpath("//input[@id = 'firstName']"));
        firstNameInput.sendKeys("John");

        WebElement lastName = driver.findElement(By.xpath("//*[@id='lastName']"));
        lastName.sendKeys("Dor");

        WebElement dateBirth = driver.findElement(By.xpath("//*[@id='birthdate']"));
        dateBirth.sendKeys("1990-12-14");

        WebElement eMail = driver.findElement(By.xpath("//*[@id='email']"));
        String uniqueEmail = generateUniqueEmail();
        eMail.sendKeys(uniqueEmail);

        WebElement phone = driver.findElement(By.xpath("//*[@id='phone']"));
        phone.sendKeys("8005555557");

        WebElement street1 = driver.findElement(By.xpath("//*[@id='street1']"));
        street1.sendKeys("1 Main St.");

        WebElement street2 = driver.findElement(By.xpath("//*[@id='street2']"));
        street2.sendKeys("Apartment B");

        WebElement city = driver.findElement(By.xpath("//*[@id='city']"));
        city.sendKeys("Oreo");

        WebElement stateProvince = driver.findElement(By.xpath("//*[@id='stateProvince']"));
        stateProvince.sendKeys("KS");

        WebElement postalCode = driver.findElement(By.xpath("//*[@id='postalCode']"));
        postalCode.sendKeys("12345");

        WebElement country = driver.findElement(By.xpath("//*[@id='country']"));
        country.sendKeys("USA");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='submit']"));
        submitButton.click();
        sleep(2000);

        WebElement ClickName = driver.findElement(By.cssSelector("tr.contactTableBodyRow"));
        ClickName.click();
        sleep(300);

        WebElement ContactDetails = driver.findElement(By.xpath("//h1[text()='Contact Details']"));
        Assert.assertEquals(ContactDetails.getText(), "Contact Details");

    }

    @AfterMethod
    public void tearDown() {
        // Закрытие браузера после каждого теста
        driver.quit();
    }

}
