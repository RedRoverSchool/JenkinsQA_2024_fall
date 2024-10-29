package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class AidaMontoyaTest {


    @Test
    public void testDutchbros() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.dutchbros.com/");

        WebElement clickButton = driver.findElement(By.xpath("//div[3]//article//a"));//find "view menu"
        clickButton.sendKeys(Keys.RETURN);
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[contains(@href,'caramel-pumpkin-brulee-breve')]")).click();//find "caramel pumpkin"
        Thread.sleep(2000);

        String message = driver.getCurrentUrl();
        System.out.println(message.equals("https://www.dutchbros.com/menu/seasonal-drinks/caramel-pumpkin-brulee-breve"));

        driver.quit();

    }

    @Test
    public void testLibrary() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://catalog.saclibrary.org/");

        driver.findElement(By.xpath("//a[starts-with(@href, 'http://linkencore.iii.com/')]")).click();// find link+ button/click
        Thread.sleep(2000);

        WebElement search = driver.findElement(By.xpath("//input[@id='searchString']"));
        search.sendKeys("Pushkik");
        search.sendKeys(Keys.RETURN);
        Thread.sleep(2000);

        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='spellingSuggestion']")), "Did you mean: pushkin?");

        driver.quit();
    }
}

