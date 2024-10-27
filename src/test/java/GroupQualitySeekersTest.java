import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupQualitySeekersTest {
    @Test
    public void bankManagerLogin() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbx");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement buttonManager = driver.findElement(By.xpath("//div[1]/div[2]/button"));
        buttonManager.click();
        WebElement buttonAddCustomer = driver.findElement(By.xpath("//div[2]/div/div[1]/button[1]"));
        Assert.assertEquals(buttonAddCustomer.getText(), "Add Customer");
        driver.quit();
    }

    WebDriver driver;

    @BeforeMethod
    public void setDriver() {
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }

    @Test
    public void testRemoveTest() {
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");

        WebElement addElement = driver.findElement(By.xpath("//*[@id=\"content\"]/div/button"));
        addElement.click();

        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"elements\"]/button"))
                .getText(), "Delete");

        driver.findElement(By.xpath("//*[@id=\"elements\"]/button")).click();
    }
    @Test
    public void selectCheckboxesTest() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement checkbox1 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[1]"));
        WebElement checkbox2 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[2]"));

        checkbox2.isSelected();

        checkbox1.click();

        checkbox1.isSelected();
    }
    @Test
    public void contextMenuTest() {
        driver.get("https://the-internet.herokuapp.com/context_menu");

        Actions action = new Actions(driver);

        WebElement contextItem = driver.findElement(By.id("hot-spot"));
        action.contextClick(contextItem).build().perform();

        Alert displayMessage = driver.switchTo().alert();

        Assert.assertEquals(displayMessage.getText(),"You selected a context menu");
    }

    @Test
    public void drugAndDropTest() {
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");

        WebElement elementA = driver.findElement(By.id("column-a"));
        WebElement elementB = driver.findElement(By.id("column-b"));

        Actions action = new Actions(driver);
        action.dragAndDrop(elementA,elementB).build().perform();

        Assert.assertEquals(elementA.getText(), "B");
    }
    @Test
    public void isDisplayedTest() {
        driver.get("https://the-internet.herokuapp.com/dynamic_content?with_content=static");

        WebElement refreshButton = driver.findElement(By.xpath("//*[@id=\"content\"]/div/p[2]/a"));
        refreshButton.click();

        WebElement updatedElement = driver.findElement(By.xpath("//*[@id=\"content\"]/div[3]"));
        Assert.assertTrue(updatedElement.isDisplayed());
    }

    @Test
    public void dynamicControlTest() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

        WebElement checkboxA = driver.findElement(By.xpath("//*[@id=\"checkbox\"]/input"));
        Assert.assertTrue(checkboxA.isDisplayed());

        WebElement removeButton = driver.findElement(By.xpath("//*[@id=\"checkbox-example\"]/button"));
        removeButton.click();

        Thread.sleep(3000);

        WebElement informMessage = driver.findElement(By.xpath("//*[@id=\"message\"]"));
        Assert.assertEquals(informMessage.getText(),"It's gone!");

        WebElement addButton = driver.findElement(By.xpath("//*[@id=\"checkbox-example\"]/button"));
        addButton.click();

        Thread.sleep(3000);

        WebElement checkboxAppeared = driver.findElement(By.xpath("//*[@id=\"checkbox\"]"));
        Assert.assertTrue(checkboxAppeared.isDisplayed());
    }

    @Test
    public void findHiddenTest() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading");

        WebElement hiddenElement = driver.findElement(By.xpath("//*[@id=\"content\"]/div/a[1]"));
        hiddenElement.click();

        driver.getCurrentUrl();

        WebElement startButton = driver.findElement(By.xpath("//*[@id=\"start\"]/button"));
        startButton.click();

        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(5));
        w.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));

        Assert.assertEquals(driver.findElement(By.id("finish")).getText(), "Hello World!");
    }

    @Test
    public void closeAd() {
        driver.get("https://the-internet.herokuapp.com/entry_ad");
        WebElement modalWindow = driver.findElement(By.xpath("//*[@id=\"modal\"]/div[2]"));

        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(1));
        w.until(ExpectedConditions.visibilityOfAllElements(modalWindow));

        WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"modal\"]/div[2]/div[3]/p"));
        closeButton.click();

        Assert.assertFalse(modalWindow.isDisplayed());

        driver.findElement(By.xpath("/html/body")).sendKeys((Keys.F5));

        WebElement reEnableButton = driver.findElement(By.xpath("//*[@id=\"restart-ad\"]"));
        reEnableButton.click();

        driver.findElement(By.xpath("/html/body")).sendKeys((Keys.F5));
        WebElement modalWindowEnable = driver.findElement(By.xpath("//*[@id=\"modal\"]/div[2]"));

        w.until(ExpectedConditions.visibilityOfAllElements(modalWindowEnable));
        Assert.assertTrue(modalWindowEnable.isDisplayed());
    }

    @Test
    public void goToHome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbx");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/manager");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
        WebElement buttonHome = driver.findElement(By.xpath("//div/div/div[1]/button[1]"));
        buttonHome.click();
        //WebElement buttonManager = driver.findElement(By.xpath("//div/div/div[2]/div/div[1]/div[2]/button"));
        WebElement buttonManager = driver.findElement(By.xpath("//div[1]/div[2]/button"));
        Assert.assertEquals(buttonManager.getText(), "Bank Manager Login");
        driver.quit();
    }
}
