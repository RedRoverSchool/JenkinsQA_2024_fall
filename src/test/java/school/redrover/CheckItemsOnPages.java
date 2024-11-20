package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class CheckItemsOnPages extends BaseTest {

    @Test
    public void checkTitlesAndButtonsOnMainPage(){

            List<WebElement> startPageMainContent = getDriver().findElements(By.className("content-block"));

            Assert.assertEquals(startPageMainContent.size(), 4);
            Assert.assertEquals(startPageMainContent.get(0).getText(), "Create a job");
            Assert.assertEquals(startPageMainContent.get(1).getText(), "Set up an agent");
            Assert.assertEquals(startPageMainContent.get(2).getText(), "Configure a cloud");
            Assert.assertEquals(startPageMainContent.get(3).getText(), "Learn more about distributed builds");

  }


}
