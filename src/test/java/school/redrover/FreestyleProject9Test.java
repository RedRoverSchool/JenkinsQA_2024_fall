package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import javax.swing.text.StyledEditorKit;
import java.time.Duration;
import java.util.Collections;
import java.util.Set;

public class FreestyleProject9Test extends BaseTest {

    @Test
    /*
    getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/textarea")).sendKeys(projectDescription);
    getDriver().findElement(By.name("Submit")).click();
    getDriver().findElement(By.xpath("//*[@id='jenkins-home-link']")).click();
    */

    public void firstTest() {

        getDriver().findElement(By.xpath("/html/body/div/div/div/div/span/a")).click();

        WebElement writeName = getDriver().findElement(By.xpath("/html/body/div/div/div/form/div/div/input"));
        writeName.click();
        writeName.sendKeys("Hello");

        getDriver().findElement(By.xpath("/html/body/div/div/div/form/div/div/div/ul/li")).click();
        getDriver().findElement(By.xpath("/html/body/div/div/div/form/div/div/button")).click();






    }
}

