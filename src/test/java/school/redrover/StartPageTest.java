package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.time.Duration;
import java.util.List;

public class StartPageTest extends BaseTest {

    private void createNewFolder(String name) {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.xpath("//a[@href = 'newJob']")).click();

        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.name("name"))));
        nameField.sendKeys(name);
        getDriver().findElement(By.xpath("//span[text() = 'Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement buttonApply = wait.until(ExpectedConditions.elementToBeClickable(getDriver().
                findElement(By.name("Apply"))));
        buttonApply.click();

        getDriver().findElement(By.xpath("//a[contains(text(), 'Dashboard')]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().
                findElement(By.id("projectstatus"))));
    }

    @Test
    public void testStartPageMainPanelContent() {

        List<WebElement> startPageMainContent = getDriver().findElements(By.className("content-block"));

        Assert.assertEquals(startPageMainContent.size(), 4);
        Assert.assertEquals(startPageMainContent.get(0).getText(), "Create a job");
        Assert.assertEquals(startPageMainContent.get(1).getText(), "Set up an agent");
        Assert.assertEquals(startPageMainContent.get(2).getText(), "Configure a cloud");
        Assert.assertEquals(startPageMainContent.get(3).getText(), "Learn more about distributed builds");
    }

    @Test
    public void testStartPageSidePanelTaskContent() {

        List<WebElement> startPageSideContent = getDriver().findElements(By.xpath("//div[contains(@class, 'task')]"));

        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(startPageSideContent.get(0).getText(), "New Item");
        Assert.assertEquals(startPageSideContent.get(1).getText(), "Build History");
        Assert.assertEquals(startPageSideContent.get(2).getText(), "Manage Jenkins");
        Assert.assertEquals(startPageSideContent.get(3).getText(), "My Views");
    }

    @Test
    public void testCheckLinksSidePanel() {

        List<WebElement> startPageSideContent = getDriver().findElements(By.xpath("//a[contains(@class, 'task-link-no-confirm')]"));

        Assert.assertEquals(startPageSideContent.size(), 4);
        Assert.assertEquals(startPageSideContent.get(0).getAttribute("href"), "http://localhost:8080/view/all/newJob");
        Assert.assertEquals(startPageSideContent.get(1).getAttribute("href"), "http://localhost:8080/view/all/builds");
        Assert.assertEquals(startPageSideContent.get(2).getAttribute("href"), "http://localhost:8080/manage");
        Assert.assertEquals(startPageSideContent.get(3).getAttribute("href"), "http://localhost:8080/me/my-views");
    }

    @Test
    public void testCreateDescription() {

        WebElement buttonAddDescriptions = getDriver().findElement(By.id("description-link"));
        buttonAddDescriptions.click();

        WebElement descriptionField = getDriver().findElement(By.xpath("//textarea[contains(@class, 'jenkins-input')]"));
        descriptionField.sendKeys("Hello World!!");

        getDriver().findElement(By.name("Submit")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement descriptionText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='description']/div[1]")));
        Assert.assertEquals(descriptionText.getText(), "Hello World!!");
    }

    @Test
    public void testCreateNewFolder() {

        final String folderName = "Fisrt_folder";
        createNewFolder(folderName);

        Assert.assertEquals(getDriver().findElement(By.className("jenkins-table__link")).getText(), folderName);
    }

    @Test
    public void testDeleteNewFolder() {

        final String folderName = "NewFolder";

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));

        createNewFolder(folderName);

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath(
                "//span[text()='NewFolder']"))).perform();

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//button[@data-href = 'http://localhost:8080/job/NewFolder/']")));
        button.click();

        WebElement buttonDelete = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//button[@href='/job/NewFolder/doDelete']//div")));
                actions.moveToElement(buttonDelete);
                buttonDelete.click();

        WebElement buttonOk = wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath(
                "//button[@data-id= 'ok']"))));
        buttonOk.click();

        WebElement secondText = getDriver().findElement(By.xpath("//p[contains(text(), 'This page is where')]"));

        Assert.assertEquals(secondText.getText(), "This page is where your Jenkins jobs will be displayed. To get started, you can set up distributed builds or start building a software project.");
    }

}
