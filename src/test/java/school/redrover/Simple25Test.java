package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static java.awt.SystemColor.text;

public class Simple25Test extends BaseTest {
    @Test
    public  void testCreateNewItem() throws InterruptedException {

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        WebElement textBox =  getDriver().findElement(By.xpath("//input[@name='name']"));
        textBox.sendKeys("New item");

        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[2]/div[2]/div")).click();
        getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]")).click();
        getDriver().findElement(By.xpath("//*[@id=\"jenkins-name-icon\"]")).click();

        WebElement elementItem = getDriver().findElement(By.xpath("//*[@id=\"job_New item\"]/td[3]/a/span"));

        Assert.assertNotNull(elementItem);
    }
}
