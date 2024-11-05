package school.redrover.old;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class HW15SeleniumTest {
    /* Необходимо написать несколько тестов на Selenium в одном
    классе и на одном сайте, можете использовать любой сайт
    (лучше не использовать сайт крупных компаний, они будут защищаться
    от ваших тестов). Без разницы какие тесты, главная задача это начать
    практиковаться в написании Selenium кода и понять как писать
    несколько тестов в одном проекте.
     */
    @Test
    public void testSelenium() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        Thread.sleep(3000);

        Assert.assertEquals(driver.getTitle(), "Web form");
        System.out.println(driver.getTitle());

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textBox = driver.findElement(By.name("my-text"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        Assert.assertEquals(message.getText(),"Received!");
        System.out.println(message.getText());

        driver.quit();
    }

    @Test
    public void testGoogle() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com/");

        Thread.sleep(1000);
        WebElement textBox = driver.findElement(By.xpath("//textarea[@id='APjFqb']"));
        textBox.sendKeys("Selenium");

        WebElement submitButton = driver.findElement(By.xpath("//body[1]/div[1]/div[3]/form[1]/div[1]/div[1]/div[4]/center[1]/input[1]"));
        submitButton.click();

        Thread.sleep(1200);
        WebElement message = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[2]/div/div/div/div/div/div/div/div[2]/div"));

        Thread.sleep(500);
        Assert.assertEquals(message.getText(),"Selenium automates browsers. That's it! What you do with that power is entirely up to you. Primarily it is for automating web applications for testing purposes.");
        System.out.println(message.getText());

        driver.quit();
    }

    @Test
    public void testZara() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.zara.com/us/en/search/home");
        Thread.sleep(1000);

        WebElement textBox = driver.findElement(By.xpath("//input[@aria-label='Search text input']"));
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        textBox.sendKeys("red");
        textBox.sendKeys(Keys.RETURN);
        Thread.sleep(1000);
        try {
            WebElement closeButton = driver.findElement(By.className("onetrust-close-btn-handler"));
            if (closeButton.isDisplayed()) {
                closeButton.click();
            }
        } catch (NoSuchElementException e) {
            //Element not found, proceed without closing.
        }
        Thread.sleep(1000);
        WebElement itemTitle = driver.findElement(By.xpath("//h2[contains(text(),'OVERSIZED 100% WOOL CARDIGAN')]"));
        Assert.assertEquals(itemTitle.getText(),"OVERSIZED 100% WOOL CARDIGAN");
        System.out.println(itemTitle.getText());

        driver.quit();
    }

    @Test
    public void testLego() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.lego.com/en-us");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement tabShop = driver.findElement(By.xpath("//button[@data-analytics-title='shop']"));
        tabShop.click();

//        WebElement message = driver.findElement(By.id("message"));
//        Assert.assertEquals(message.getText(),"Received!");
//        System.out.println(message.getText());

        driver.quit();
    }
}



