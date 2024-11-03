package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class SearchTest extends BaseTest {
    @Test
    public void testSearchManage() {
        WebElement search = getDriver().findElement(By.xpath("//input[@id='search-box']"));
        search.click();
        search.sendKeys("manage");
        search.sendKeys(Keys.ENTER);

        String name = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div/div[1]/h1")).getText();
        Assert.assertEquals(name,"Manage Jenkins");


    }
}
