import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class GroupWnFTest {

    private static final String USER_NAME = "Akiko";
    private static final String USER_EMAIL = "iakikoaokii@gmail.com";
    private static final String USER_ADDRESS = "Moscow";
    private static final String USER_PER_ADDRESS = "Street 1, House 1";
    private static final String URL = "https://websters.dev/";
    private static WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterTest
    public void closeUp() {
        driver.quit();
    }

    @Test
    public void testDemoQATextBox() {
        driver.get("https://demoqa.com/text-box");

        driver.findElement(By.id("userName")).sendKeys(USER_NAME);
        driver.findElement(By.id("userEmail")).sendKeys(USER_EMAIL);
        driver.findElement(By.id("currentAddress")).sendKeys(USER_ADDRESS);
        driver.findElement(By.id("permanentAddress")).sendKeys(USER_PER_ADDRESS);

        driver.findElement(By.xpath("//*[@id='submit']")).click();

        WebElement actualName = driver.findElement(By.id("name"));
        WebElement actualEmail = driver.findElement(By.id("email"));
        WebElement actualAddress = driver.findElement(By.xpath("//p[@id='currentAddress']"));
        WebElement actualPerAddress = driver.findElement(By.xpath("//p[@id='permanentAddress']"));

        Assert.assertEquals(actualName.getText(), "Name:" + USER_NAME);
        Assert.assertEquals(actualEmail.getText(), "Email:" + USER_EMAIL);
        Assert.assertEquals(actualAddress.getText(), "Current Address :" + USER_ADDRESS);
        Assert.assertEquals(actualPerAddress.getText(), "Permananet Address :" + USER_PER_ADDRESS);
    }

    @Test
    public void testDemoQACheckboxes() {
        driver.get("https://qa-practice.netlify.app/checkboxes");

        List<WebElement> checkBoxes = driver
                .findElements(By.xpath("//div[@class='form-group']//input[@type='checkbox']"));

        for (WebElement checkbox : checkBoxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
        
        for (WebElement checkBox : checkBoxes) {
            Assert.assertTrue(checkBox.isSelected(),"Checkbox ID: " + checkBox.getAttribute("id") + " is not selected");
        }
    }

    @Test
    public void testMango() {

        driver.get("https://shop.mango.com/uz/en");
        driver.getTitle();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        // Нашли кнопку с кукисами и нажали "accept all cookies"
        WebElement submitButton = driver.findElement(By.id("cookies.button.acceptAll"));
        submitButton.click();

        // Нааходим кнопку "Accept", что мы в Узбекистане
        WebElement submitButton1 = driver.findElement(By.id("changeCountryAccept"));
        submitButton1.click();

        // Нашли кнопку Woman и перешли в раздел женской одежды
        WebElement submitButton3 = driver.findElement(By.xpath("//a[contains(@href, 'women/promotion_7914393e')]"));
        submitButton3.click();

        // Сравниваем URL ожидаемый и фактический
        String expectedUrl = "https://shop.mango.com/uz/en/c/women/promotion_7914393e";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "URL не соответствует ожидаемому!");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        // Ищем кнопку "добавить в избранное" (сердечко wishlist под товаром)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement favouritesElement = driver.findElement(
                By.xpath("/html/body/div/main/div/div[4]/ul/li[2]/div/div/div[1]/div[2]/div[1]/div/button"));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        favouritesElement.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        Assert.assertNotNull(favouritesElement, "Кнопка 'Добавить в избранное' не найдена!");

        // Проверяем, что элемент видим и доступен для клика
        Assert.assertTrue(favouritesElement.isDisplayed(), "Кнопка 'Добавить в избранное' не видима!");
        Assert.assertTrue(favouritesElement.isEnabled(), "Кнопка 'Добавить в избранное' недоступна для клика!");

        // Ищем и нажимаем кнопку wishlist
        WebElement wishListElement = driver.findElement(By.xpath("/html/body/div/header/div[1]/div[3]/ul/li[3]/a"));
        wishListElement.click();

        // Сравниваем второй URL
        String expectedUrl2 = "https://shop.mango.com/uz/en/favorites";
        String actualUrl2 = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl2, expectedUrl2, "URL не соответствует ожидаемому!");

    }

    @Test
    public static void startTest() throws InterruptedException {
        driver.get(URL);
        Thread.sleep(1500);
        WebElement mainTitle = driver.findElement(By.xpath("//h1"));
        Assert.assertEquals(mainTitle.getText().toUpperCase(), "WEBSTERS");
    }

    @Test
    public static void modalFormTest() {
        driver.get(URL);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));

        WebElement headerButton = driver.findElement(By.xpath("//button[contains(@class, 'Header_button')]"));
        headerButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));

        WebElement nameField = driver
                .findElement(By.xpath("//label[@for='name' and contains(@class, 'Modal')]//input"));
        nameField.sendKeys("Иван Иванов");

        WebElement emailField = driver
                .findElement(By.xpath("//label[@for='email' and contains(@class, 'Modal')]//input"));
        emailField.sendKeys("oaogku@fexbox.org");

        WebElement messageField = driver
                .findElement(By.xpath("//label[@for='message' and contains(@class, 'Modal')]//textarea"));
        messageField.sendKeys("поставьте капчу =)");

        WebElement submitButton = driver
                .findElement(By.xpath("//button[@type='submit' and contains(@class, 'Modal')]"));
        submitButton.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1500));

        WebElement finishModal = driver.findElement(By.xpath("//div[contains(@class, 'Modal_finish')]//p"));

        Assert.assertEquals(finishModal.getText(), "Thank you! Our manager will contact you as soon as possible");
    }
}