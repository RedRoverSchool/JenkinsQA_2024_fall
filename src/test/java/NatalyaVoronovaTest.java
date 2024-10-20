import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
public class NatalyaVoronovaTest {
    @Test
    public void testHeadless() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://demoqa.com/");
        String title = driver.getTitle();

        Assert.assertEquals(title, "DEMOQA");
        System.out.println("Это вывод на печать заголовка страницы сайта без демонстрации экрана: " + title);
        driver.quit();
    }

    @Test
    public void testForm() throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://demoqa.com/");
//        Thread.sleep(2000);

        WebElement forms = driver.findElement(By.xpath("//div/div/div[2]/div/div[2]"));
        forms.click();

        WebElement pracriceForms = driver.findElement(By.cssSelector(".element-list.collapse.show"));
        pracriceForms.click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://demoqa.com/automation-practice-form");
        String titleForm = driver.findElement(By.cssSelector(".text-center")).getText();
        Assert.assertEquals(titleForm, "Practice Form");


        WebElement firstname = driver.findElement(By.id("firstName"));
        firstname.sendKeys("Nata");

        WebElement lastName = driver.findElement(By.id("lastName"));
        lastName.sendKeys("fff");

        driver.findElement(By.xpath("//label[contains(text(), 'Male')]")).click();
        driver.findElement(By.id("userNumber")).sendKeys("1234567890");
        Thread.sleep(1000);
        driver.findElement(By.id("submit")).click();
        Thread.sleep(1000);

        String modalTitle = driver.findElement(By.id("example-modal-sizes-title-lg")).getText();
        Assert.assertEquals("Thanks for submitting the form", modalTitle);

        String firstNameValue = driver.findElement(By.xpath("//td[text()='Student Name']/following-sibling::td")).getText();
        Assert.assertEquals("Nata fff", firstNameValue);

        String phoneValue = driver.findElement(By.xpath("//td[text()='Mobile']/following-sibling::td")).getText();
        Assert.assertEquals("1234567890", phoneValue);
        Thread.sleep(1500);
        driver.quit();
    }
}
