package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.List;

public class StartPageTest extends BaseTest {

    @Test
    public void testStartPageMainPanelContent(){

        List<WebElement> startPageMainContent = getDriver().findElements(By.className("content-block"));

        Assert.assertEquals(startPageMainContent.size(), 4);
        Assert.assertEquals(startPageMainContent.get(0).getText(), "Create a job");
        Assert.assertEquals(startPageMainContent.get(1).getText(), "Set up an agent");
        Assert.assertEquals(startPageMainContent.get(2).getText(), "Configure a cloud");
        Assert.assertEquals(startPageMainContent.get(3).getText(), "Learn more about distributed builds");
    }

    @Test
    public void testStartPageSidePanelTaskContent(){

        List<WebElement> startPageSideContent = getDriver().findElements(By.xpath("//div[contains(@class, 'task')]"));

        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(startPageSideContent.get(0).getText(), "New Item");
        Assert.assertEquals(startPageSideContent.get(1).getText(), "Build History");
        Assert.assertEquals(startPageSideContent.get(2).getText(), "Manage Jenkins");
        Assert.assertEquals(startPageSideContent.get(3).getText(), "My Views");
    }

    @Test
    public void testCheckLinksSidePanel(){

        List<WebElement> startPageSideContent = getDriver().findElements(By.xpath("//a[contains(@class, 'task-link-no-confirm')]"));

        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(startPageSideContent.get(0).getAttribute("href"), "http://localhost:8080/view/all/newJob");
        Assert.assertEquals(startPageSideContent.get(1).getAttribute("href"), "http://localhost:8080/view/all/builds");
        Assert.assertEquals(startPageSideContent.get(2).getAttribute("href"), "http://localhost:8080/manage");
        Assert.assertEquals(startPageSideContent.get(3).getAttribute("href"), "http://localhost:8080/me/my-views");
    }

    @Test
    public void testCreateDescription() throws InterruptedException {

        WebElement buttonAddDescriptions = getDriver().findElement(By.id("description-link"));
        buttonAddDescriptions.click();

        WebElement descriptionField = getDriver().findElement(By.xpath("//textarea[contains(@class, 'jenkins-input')]"));
        descriptionField.sendKeys("Hello World!!");

        WebElement buttonSave = getDriver().findElement(By.name("Submit"));
        buttonSave.click();
        Thread.sleep(1000);

        WebElement descriptionText = getDriver().findElement(By.xpath("//div[@id='description']/div[1]"));
        Assert.assertEquals(descriptionText.getText(), "Hello World!!");
    }
}
