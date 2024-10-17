import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



public class KhPaulTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        // Инициализация WebDriver
        driver = new ChromeDriver();
        driver.get("https://anixtd.ru/contacts.php");

        // Заполнение формы перед каждым тестом
        WebElement userName = driver.findElement(By.name("user_name"));
        userName.sendKeys("Test testov");

        WebElement eMail = driver.findElement(By.name("user_email"));
        eMail.sendKeys("sdfvjkhb@jhbvsd.ru");

        WebElement message = driver.findElement(By.name("MESSAGE"));
        message.sendKeys("проверка связи");

        WebElement submitButton = driver.findElement(By.cssSelector(".faq_sub"));
        submitButton.click();
        //WebElement regButton = driver.findElement(By.xpath("//input[@value='Register an account']"));
        //regButton.click();
        //Thread.sleep(1000); ожидание
        //submitButton.sendKeys(Keys.RETURN); нажатеи на энтер
    }

    @Test
    public void testErrorTextAfterSendMessage() {
        WebElement errorMessage = driver.findElement(By.cssSelector(".errortext"));
        Assert.assertEquals(errorMessage.getText(), "Вы не прошли проверку подтверждения личности");
    }

    @Test
    public void testErrorTextColor() {
        WebElement errorMessage = driver.findElement(By.cssSelector(".errortext"));
        String color = errorMessage.getCssValue("color");
        String expectedColor = "rgba(255, 0, 0, 1)";
        Assert.assertEquals(color, expectedColor);

    }

    @AfterMethod
    public void tearDown() {
        // Закрытие браузера после каждого теста
        driver.quit();
    }
}
