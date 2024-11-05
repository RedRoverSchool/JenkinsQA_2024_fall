package school.redrover.runner;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstPageTest extends BaseTest{
    @Test
    public void testDescription(){
        String descriptionStr = getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(3) > div > p")).getText();
        Assert.assertEquals(descriptionStr,"This page is where your Jenkins jobs will be displayed. To get started, you can set up distributed builds or start building a software project.");
}
}
