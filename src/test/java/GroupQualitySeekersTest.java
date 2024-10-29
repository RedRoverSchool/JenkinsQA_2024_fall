import org.openqa.selenium.*;
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

    WebDriver driver;

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }

    @BeforeMethod
    public void setDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

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

    @Test
    public void testSelectCheckboxes() {

        driver.get("https://the-internet.herokuapp.com/checkboxes");

        WebElement checkbox1 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[1]"));
        WebElement checkbox2 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[2]"));

        checkbox2.isSelected();

        checkbox1.click();

        checkbox1.isSelected();
    }

    @Test
    public void testContextMenu() {

        driver.get("https://the-internet.herokuapp.com/context_menu");

        Actions action = new Actions(driver);

        WebElement contextItem = driver.findElement(By.id("hot-spot"));
        action.contextClick(contextItem).build().perform();

        Alert displayMessage = driver.switchTo().alert();

        Assert.assertEquals(displayMessage.getText(), "You selected a context menu");
    }

    @Test
    public void testDrugAndDrop() {

        driver.get("https://the-internet.herokuapp.com/drag_and_drop");

        WebElement elementA = driver.findElement(By.id("column-a"));
        WebElement elementB = driver.findElement(By.id("column-b"));

        Actions action = new Actions(driver);
        action.dragAndDrop(elementA, elementB).build().perform();

        Assert.assertEquals(elementA.getText(), "B");
    }

    @Test
    public void testDynamicControl() throws InterruptedException {

        driver.get("https://the-internet.herokuapp.com/dynamic_controls");

        WebElement checkboxA = driver.findElement(By.xpath("//*[@id=\"checkbox\"]/input"));
        Assert.assertTrue(checkboxA.isDisplayed());

        WebElement removeButton = driver.findElement(By.xpath("//*[@id=\"checkbox-example\"]/button"));
        removeButton.click();

        Thread.sleep(3000);

        WebElement informMessage = driver.findElement(By.xpath("//*[@id=\"message\"]"));
        Assert.assertEquals(informMessage.getText(), "It's gone!");

        WebElement addButton = driver.findElement(By.xpath("//*[@id=\"checkbox-example\"]/button"));
        addButton.click();

        Thread.sleep(3000);

        WebElement checkboxAppeared = driver.findElement(By.xpath("//*[@id=\"checkbox\"]"));
        Assert.assertTrue(checkboxAppeared.isDisplayed());
    }

    @Test
    public void testFindHiddenTest() {

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
    public void testCloseAd() {

        driver.get("https://the-internet.herokuapp.com/entry_ad");
        WebElement modalWindow = driver.findElement(By.xpath("//*[@id=\"modal\"]/div[2]"));

        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(2));
        w.until(ExpectedConditions.visibilityOfAllElements(modalWindow));

        WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"modal\"]/div[2]/div[3]/p"));
        closeButton.click();

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

        WebElement buttonManager = driver.findElement(By.xpath("//div[1]/div[2]/button"));
        Assert.assertEquals(buttonManager.getText(), "Bank Manager Login");
        driver.quit();
    }


    @Test
    public void testTitle() {

        driver.get("https://beta.chelznak.ru/made/ ");

        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Изготовление наград на заказ в ТПП Челзнак");
    }

    @Test
    public void test2() {

        driver.get("https://beta.chelznak.ru/made/");

        WebElement textBox = driver.findElement(By.xpath("/html/body/div[1]/header/div[2]/div/div/div[1]/div/form/input"));
        textBox.sendKeys("Награда МВД");
        textBox.sendKeys(Keys.ENTER);

        String AssertMVD = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div[1]/h1")).getText();
        Assert.assertEquals(AssertMVD, "РЕЗУЛЬТАТОВ ПО ЗАПРОСУ «НАГРАДА МВД»: 235");
    }

    @Test
    public void test3() throws InterruptedException {

        driver.get("https://beta.chelznak.ru/made/");

        WebElement imagePhone = driver.findElement(By.xpath("/html/body/div[1]/header/div[2]/div/div/div[2]/div[2]/a[1]"));
        imagePhone.click();
        Thread.sleep(1000);

        String QuestionsAnswers = driver.findElement(By.xpath("//*[@id='modal-faq']/div[1]/div")).getText();
        Assert.assertEquals(QuestionsAnswers, "ВОПРОСЫ И ОТВЕТЫ");
    }


}


