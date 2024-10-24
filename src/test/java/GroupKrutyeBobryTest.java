import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
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

public class GroupKrutyeBobryTest {

    @Test
    public void testWaitForLoadingPicture() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        Boolean textInp = wait.until(ExpectedConditions.textToBe(By.id("text"), "Done!"));
        Assert.assertEquals(textInp, true);

        driver.quit();
    }

    @Test
    public void testMouseActionDropDown() throws InterruptedException {

        WebDriver driver = new ChromeDriver();

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");

        Actions actions = new Actions(driver);

        WebElement dropDown2 = driver.findElement(By.id("my-dropdown-2"));
        WebElement contextMenu2 = driver.findElement(By.id("context-menu-2"));
        actions.contextClick(dropDown2).perform();
        Assert.assertTrue(contextMenu2.isDisplayed());

        WebElement dropDown3 = driver.findElement(By.id("my-dropdown-3"));
        WebElement contextMenu3 = driver.findElement(By.id("context-menu-3"));
        actions.doubleClick(dropDown3).perform();
        Assert.assertTrue(contextMenu3.isDisplayed());

        WebElement dropDown1 = driver.findElement(By.id("my-dropdown-1"));
        actions.click(dropDown1).perform();

        WebElement contextMenu1 = driver.findElement(By.xpath("//ul[@class='dropdown-menu show']"));
        Assert.assertTrue(contextMenu1.isDisplayed());

        driver.quit();
    }

    @Test
    public void testFindPageTitleHeadless(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://magento.softwaretestingboard.com/");
        String title = driver.getTitle();
        Assert.assertEquals(title, "Home Page");
        System.out.println("Page title: " + title);
        driver.quit();
    }
}
