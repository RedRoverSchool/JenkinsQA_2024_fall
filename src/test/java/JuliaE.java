import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class JuliaE {

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
}

