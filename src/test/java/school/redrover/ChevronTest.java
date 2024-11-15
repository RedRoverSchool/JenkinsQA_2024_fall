package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

public class ChevronTest extends BaseTest {

    @Test
    public void testDropdownChevron() {

        Actions actions = new Actions(getDriver());

        WebElement chevronDashboard = getDriver().findElement(By.xpath("//li/a/button[@class='jenkins-menu-dropdown-chevron']"));
        actions
                .moveToElement(chevronDashboard)
                .pause(1000)
                .moveToElement(chevronDashboard)
                .click(chevronDashboard)
                .pause(2000)
                .perform();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/a[@href='/view/all/builds']")).getText(),
                "Build History");

        ProjectUtils.log(getDriver().findElement(By.xpath("//div/a[@href='/view/all/builds']")).getText());

    }

}
