package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject8Test extends BaseTest {

    @Test
    public void testRenameProject() {
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.id("name")).sendKeys("fuigdjghf");
        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[7]/span/a")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys("NewFreestyleProject");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[1]/div[1]/h1")).getText(), "NewFreestyleProject");
    }
}
