package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static java.sql.DriverManager.getDriver;

public class NewFolderTest extends BaseTest {

    @Test
    public void testNewItemFolder() throws InterruptedException {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        WebElement textInput = getDriver().findElement(By.xpath("//input[@class='jenkins-input']"));
        textInput.click();
        textInput.sendKeys("FirstFolder");

        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]/div[2]/div")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).sendKeys("admin");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String folderName = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();

        Assert.assertEquals(folderName, "admin");

    }
}

