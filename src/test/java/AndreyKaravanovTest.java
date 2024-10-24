import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.swing.*;
import java.time.Duration;


public class AndreyKaravanovTest {
    @Test
    public  void testDemoQaForms () throws InterruptedException {
        WebDriver driver=new ChromeDriver();
        driver.get("https://demoqa.com/forms");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement firstStep = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[1]/div/div/div[2]/div/ul/li/span"));
        firstStep.click();


        WebElement name = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[1]/div[2]/input"));
        name.sendKeys("Andrey");

        WebElement lastName = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[1]/div[4]/input"));
        lastName.sendKeys("Karavanov");

        WebElement email = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[2]/div[2]/input"));
        email.sendKeys("QWERTY@mail.ru");

        WebElement gender = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[3]/div[2]/div[1]"));
        gender.click();

        WebElement mobileNumber = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[4]/div[2]/input"));
        mobileNumber.sendKeys("8977977977");

        WebElement date = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[5]/div[2]/div[1]/div/input"));
        date.sendKeys(Keys.CONTROL+"a");
        date.sendKeys("19 Apr 1993");
        date.sendKeys(Keys.RETURN);

        //Как  тестируются псевдоэлемнты По хпасу или цсс их брать?
        WebElement hobby = driver.findElement(By.xpath("//label[text()='Sports']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", hobby);

        WebElement addres = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[9]/div[2]/textarea"));
        addres.sendKeys("улица Пушкина, дом Колотушкина 9-2");

        WebElement state = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[10]/div[2]/div/div/div[1]"));
        state.click();
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        actions.sendKeys(Keys.ENTER).perform();

        WebElement submit = driver.findElement(By.xpath("//html/body/div[2]/div/div/div/div[2]/div[2]/form/div[11]/div/button"));
        submit.click();

        WebElement message = driver.findElement(By.xpath("//html/body/div[4]/div/div/div[1]/div"));
        Assert.assertEquals(message.getText(), "Thanks for submitting the form");

        driver.quit();

    }
}
