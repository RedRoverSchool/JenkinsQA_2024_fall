import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JulieVPTest {
    @Test
    public void testMcDonalds() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.mcdonalds.com/us/en-us.html");

        WebElement submitMenuButton = driver.findElement(By.xpath("//*[@id='container-1e52aa8d39']/div/div[2]/div/div/div[2]/div/div[2]/div/nav/ul/li[1]/button"));
        submitMenuButton.click();

        Thread.sleep(1000);

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='desktop-nav-1498826098']/div/div/ul/li[1]/a/span"));
        submitButton.click();

        Thread.sleep(1000);

        WebElement itemTitle = driver.findElement(By.xpath("//*[@id='title-34dd02d5b0']/h1"));
        Assert.assertEquals(itemTitle.getText(), "Chicken Big Mac®");

        driver.quit();
    }

    @Test
    public void testLevi() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.levi.com/global");

        WebElement submitLocationButton = driver.findElement(By.xpath("//html/body/div/ul/li[5]/a"));
        submitLocationButton.click();

        Thread.sleep(500);

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='data-drilldown-5']/div[3]/div/a"));
        submitButton.click();

        Thread.sleep(1000);

        WebElement submitChoiceButton = driver.findElement(By.xpath("//*[@id='app']/div/div[2]/div[1]/div[1]/section[3]/div/div[1]/div/div/div/div/div/div/div[4]/a[2]/span"));
        submitChoiceButton.click();

        WebElement itemTitle = driver.findElement(By.xpath("//*[@id='app']/div/div[2]/div[1]/div[1]/div[2]/div/div/div/h1"));
        Assert.assertEquals(itemTitle.getText(), "WOMEN'S CLOTHING NEW ARRIVALS");

        driver.quit();
    }

    @Test
    public void testAdidas() throws InterruptedException {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.adidas.com/us");

        WebElement textBox = driver.findElement(By.xpath("//*[@id='__next']/div[3]/div[2]/div/header/div[2]/div/div[2]/div[1]/input"));
        textBox.sendKeys("jacket");
        Thread.sleep(500);
        textBox.sendKeys(Keys.RETURN);

        Thread.sleep(1000);

        WebElement itemTitle = driver.findElement(By.xpath("//*[@id='__next']/div[2]/main/section[2]/article[1]/div/a/footer/p[1]"));
        Assert.assertEquals(itemTitle.getText(), "Essentials 3-Stripes Light Down Jacket");

        driver.quit();
    }
}
