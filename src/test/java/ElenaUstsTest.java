import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ElenaUstsTest {
    private WebDriver driver;
    @BeforeMethod
    private void openDriver() throws InterruptedException{
        this.driver = new ChromeDriver();
        driver.get("https://demoqa.com/");
        Thread.sleep(1000);
    }
    @Test
    public void openBookStoreTest() {
        WebElement bookStore = driver.findElement(By.xpath("//div[@class='card-body']//h5[text()='Book Store Application']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", bookStore);
        bookStore.click();
        String newUrl = driver.getCurrentUrl();

        Assert.assertEquals(newUrl, "https://demoqa.com/books");
    }
    @Test
    public void checkTestBoxTest() {
        WebElement element = driver.findElement(By.cssSelector(".avatar.mx-auto.white"));
        element.click();
        WebElement textBox = driver.findElement(By.xpath("//span[text()='Text Box']"));
        textBox.click();
        WebElement userName = driver.findElement(By.id("userName"));
        userName.sendKeys("Vasya Petrov");
        WebElement userEmail = driver.findElement(By.id("userEmail"));
        userEmail.sendKeys("Vasya@gmail.com");
        WebElement currentAddress = driver.findElement(By.id("currentAddress"));
        currentAddress.sendKeys("Belarus, Minsk Pervomaiskaya str.71-15");
        WebElement permanentAddress = driver.findElement(By.id("permanentAddress"));
        permanentAddress.sendKeys("Belarus, Minsk Pervomaiskaya str.71-15");
        WebElement submit = driver.findElement(By.id("submit"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", submit);
        submit.click();
        WebElement output = driver.findElement(By.id("output"));
        String description = output.getText().trim().toLowerCase().replace("\n", "").replace("\\n", "");
        String expectedDescription = "name:vasya petrovemail:vasya@gmail.comcurrent address :belarus, minsk pervomaiskaya str.71-15permananet address :belarus, minsk pervomaiskaya str.71-15";
        Assert.assertEquals(description, expectedDescription);
    }
    @Test
    public void doubleClickCheckTest() throws InterruptedException{
        WebElement element = driver.findElement(By.cssSelector(".avatar.mx-auto.white"));
        element.click();
        Thread.sleep(1000);
        WebElement buttons = driver.findElement(By.xpath("//span[text()='Buttons']"));
        buttons.click();
        WebElement doubleClick = driver.findElement(By.xpath("//button[text()='Double Click Me']"));
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClick).perform();
        WebElement doubleClickMessage = driver.findElement(By.xpath("//p[text()='You have done a double click']"));
        String actualResult = doubleClickMessage.getText().trim().toLowerCase();
        String expextedresult = "You have done a double click".trim().toLowerCase();
        Assert.assertEquals(actualResult, expextedresult);
    }
    @Test
    public void rightClickCheckTest() throws InterruptedException{
        WebElement element = driver.findElement(By.cssSelector(".avatar.mx-auto.white"));
        element.click();
        Thread.sleep(2000);
        WebElement buttons = driver.findElement(By.xpath("//span[text()='Buttons']"));
        buttons.click();
        WebElement rightClick = driver.findElement(By.xpath("//button[text()='Right Click Me']"));
        Actions actions = new Actions(driver);
        actions.contextClick(rightClick).perform();
        Thread.sleep(1000);
        WebElement rightClickMessage= driver.findElement(By.id("rightClickMessage"));
        String actualResult = rightClickMessage.getText().trim().toLowerCase();
        String expextedresult = "You have done a right click".trim().toLowerCase();
        Assert.assertEquals(actualResult, expextedresult);
    }
    @AfterMethod
    private void closeBrowser(){
        driver.quit();
    }



}
