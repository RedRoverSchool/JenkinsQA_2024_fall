import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import org.openqa.selenium.NoSuchElementException;

public class GroupQAMyWayTest {

    @Test
    public void testGoogleDoodles() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");


        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/");


        WebElement button = driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[4]/center/input[2]"));
        Thread.sleep(500);

        button.click();

        WebElement about = driver.findElement(By.linkText("About"));
        Thread.sleep(500);

        about.click();

        WebElement result = driver.findElement(By.xpath("//*[@id='section-1']/div[1]/div/section/h1"));

        Assert.assertEquals(result.getText(), "The story behind Doodles");

        driver.quit();
    }

    @Test
    public void testTinkoff() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver();
        //  driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("https://www.tbank.ru/");


        WebElement card = driver.findElement(By.xpath("//html/body/div[1]/div/div[3]/div/div[2]/div/div/ul/li[1]/div/a/span/div[1]"));
        Thread.sleep(500);

        card.click();

        WebElement field = driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[1]/div[2]/div/div/p"));

        Assert.assertEquals(field.getText(), "Дебетовая карта — лучший способ оплачивать покупки и получать кэшбэк. С дебетовыми картами " +
                "Т‑Банка клиенты получают кэшбэк рублями или бонусами за каждую покупку");

        driver.quit();
    }

    @Test
    public void testRedRover() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver();

        driver.get("https://redrover.school/?lang=en");

        WebElement about = driver.findElement(By.linkText("About school"));
        Thread.sleep(500);
        about.click();

        WebElement text = driver.findElement(By.className("about-us-title"));
        Thread.sleep(500);

        Assert.assertEquals(text.getText(), "About our school");

        driver.quit();

    }

    @Test
    public void testLacoste() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");


        WebDriver driver = new ChromeDriver(options);


        driver.get("https://global.lacoste.com/en/homepage");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='didomi-notice-agree-button']"));
        submitButton.click();


        WebElement textBox = driver.findElement(By.xpath("//*[@id='search-input']"));
        textBox.sendKeys("black");
        Thread.sleep(500);
        textBox.sendKeys(Keys.RETURN);

        Thread.sleep(1000);


        driver.quit();
    }

    @Test
    public void testPostMarket() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");


        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.postmarket.am/");

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='full-width-tabpanel-0']/div/div/div/div/div[3]/div/div/div[1]/a/div/div/p"));
        submitButton.click();

        Thread.sleep(1000);


        WebElement submitButton1 = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div[2]/div[3]/button"));
        submitButton1.click();
        Thread.sleep(4000);


        driver.quit();
    }

    @Test
    public void testMonthlyWeatherTitle() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://weather.com/");

        Thread.sleep(5000);

        WebElement monthlyWeather = driver.findElement(By.xpath("//nav/div/div[1]/a[6]/span"));
        monthlyWeather.getText();
        Thread.sleep(500);
        Assert.assertEquals(monthlyWeather.getText(), "Monthly");

        driver.quit();

    }

    @Test
    public void testMonthlyWeatherLink() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://weather.com/");

        Thread.sleep(500);

        WebElement monthlyWeather = driver.findElement(By.xpath("//nav/div/div[1]/a[6]/span"));
        monthlyWeather.click();

        Thread.sleep(500);

        String monthlyUrl = driver.getCurrentUrl();
        System.out.println(monthlyUrl);


        String expectedUrl = "https://weather.com/weather/monthly/";

        String urlNotMatching = "The URL does not start with https://weather.com/weather/monthly/";

        Assert.assertTrue(monthlyUrl.startsWith(expectedUrl), urlNotMatching);

        //or
        Assert.assertTrue(monthlyUrl.contains(expectedUrl), urlNotMatching);

        driver.quit();
    }

    @Test
    public void anotherWeatherComTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        driver.get("https://weather.com/");

        WebElement privacyPopUpCloseButton = driver.findElement(By.cssSelector("#privacy-data-notice > section > button > svg > path:first-of-type"));
        privacyPopUpCloseButton.click();

        try {
            boolean isPopUpPresent = driver.findElement(By.id("privacy-data-notice")).isDisplayed();
            Assert.assertFalse(isPopUpPresent, "The privacy pop-up should be closed, but it is still visible.");
        } catch (NoSuchElementException e) {
            Assert.assertTrue(true, "Privacy pop-up is closed.");
        }
    }

    @Test
    public void testGoogle() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com/");

        driver.findElement(By.name("q")).sendKeys("bear");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);

        WebElement bear = driver.findElement(By.xpath("//div[@role='heading'][text()='Bears']"));
        Assert.assertEquals(bear.getText(), "Bears");

        driver.quit();
    }

    @Test
    public void testLogin() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://practicetestautomation.com/practice-test-login/");

        driver.findElement(By.xpath("//input[@name='username']")).sendKeys("student");
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Password123");

        driver.findElement(By.xpath("//button[@id='submit']")).click();

        String expectedUrl = "https://practicetestautomation.com/logged-in-successfully/";
        String actualUrl = driver.getCurrentUrl();

        if (actualUrl.equals(expectedUrl)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed!");
        }

        driver.quit();
    }
}

