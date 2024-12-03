package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class Simple002Test extends BaseTest {

    private static final String PROJECT_NAME = "NewFreestyleProject";
    private static final String PROJECT_DESCRIPTION = "About my new freestyle project";

    @Test
    public void testLinkLogOut() {

        WebElement linkLogOut = getDriver().findElement(By.cssSelector("a[href^='/logout']"));

        Assert.assertNotNull(linkLogOut);
    }

    @Test
    public void testLinkSetUpDistriBuildComp() {

        WebElement linkSetUpDistriBuildComp = getDriver().findElement(By.cssSelector("a[href$='computer/new']"));

        Assert.assertNotNull(linkSetUpDistriBuildComp);
    }

    @Test
    public void testLinkSetUpDistriBuildCloud() {

        WebElement linkSetUpDistriBuildCloud= getDriver().findElement(By.cssSelector("a[href$='cloud/']"));

        Assert.assertNotNull(linkSetUpDistriBuildCloud);
    }

    @Test
    public void testLinkSetUpDistriBuildLearnMore() {

        WebElement linkSetUpDistriBuildLearnMore = getDriver().findElement(By.cssSelector("a[href$='distributed-builds']"));

        Assert.assertNotNull(linkSetUpDistriBuildLearnMore);
    }

    @Test
    public void testCheckProjectName() {

        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .gotoHomePage()
                .getItemName(PROJECT_NAME);

        Assert.assertEquals(projectName, PROJECT_NAME);
    }

    @Test (dependsOnMethods = "testCheckProjectName")
    public void testAddDescription() {

        String  textDescription = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .editDescription(PROJECT_DESCRIPTION)
                .getDescription();

        Assert.assertEquals(textDescription, PROJECT_DESCRIPTION);
    }

}
