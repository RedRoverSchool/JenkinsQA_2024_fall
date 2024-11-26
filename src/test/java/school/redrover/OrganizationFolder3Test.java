package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder3Test extends BaseTest {

    String NAME_FOLDER = "Organization Folder";
    String NAME = "Name Organization Folder";
    String NEW_NAME = "New Name Organization Folder";
    String DESCRIPTION = "Description Organization Folder";
    String NEW_DESCRIPTION = "New Description Organization Folder";

    private void clickElement(By by) {
        getDriver().findElement(by).click();
    }

    private String textElement(By by) {
        return getDriver().findElement(by).getText();
    }

    private void goConfigure() {
        clickElement(By.xpath("//td/a[@class='jenkins-table__link model-link inside']"));
        clickElement(By.xpath("//a[@href='./configure']"));
    }

    @Test
    public void testCreate() {
        clickElement(By.linkText("Create a job"));
        getDriver().findElement(By.name("name")).sendKeys(NAME_FOLDER);
        clickElement(By.xpath("//li[contains(@class,'jenkins_branch_OrganizationFolder')]"));
        clickElement(By.id("ok-button"));
        clickElement(By.id("jenkins-home-link"));

        Assert.assertEquals(
                textElement(By.xpath("//div[contains(@class,'jenkins-table__cell__button-wrapper')]")),
                NAME_FOLDER
        );
    }

    @Test(dependsOnMethods = "testCreate")
    public void testAddName() {
        goConfigure();
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(NAME);
        clickElement(By.name("Submit"));

        Assert.assertEquals(textElement(By.tagName("h1")), NAME);
    }

    @Test(dependsOnMethods = {"testCreate", "testAddName"})
    public void testEditName() {
        goConfigure();
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(Keys.LEFT_CONTROL + "a");
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(NEW_NAME);
        clickElement(By.name("Submit"));

        Assert.assertEquals(textElement(By.tagName("h1")), NEW_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testAddDescription() {
        goConfigure();
        getDriver().findElement(By.name("_.description")).sendKeys(DESCRIPTION);
        clickElement(By.name("Submit"));

        Assert.assertEquals(textElement(By.id("view-message")), DESCRIPTION);
    }

    @Test(dependsOnMethods = {"testCreate", "testAddDescription"})
    public void testEditDescription() {
        goConfigure();
        getDriver().findElement(By.name("_.description")).sendKeys(Keys.LEFT_CONTROL + "a");
        getDriver().findElement(By.name("_.description")).sendKeys(NEW_DESCRIPTION);
        clickElement(By.name("Submit"));

        Assert.assertEquals(textElement(By.id("view-message")), NEW_DESCRIPTION);
    }

    @Test(dependsOnMethods = {"testAddDescription", "testEditDescription", "testAddName", "testEditName", "testCreate", "testConfigureTheProject"})
    public void testDelete() {
        clickElement(By.xpath("//td/a[@class='jenkins-table__link model-link inside']"));
        clickElement(By.xpath("//a[@data-title='Delete Organization Folder']"));
        clickElement(By.xpath("//button[@data-id='ok']"));

        Assert.assertEquals(textElement(By.tagName("h1")), "Welcome to Jenkins!");
    }


    @Test(dependsOnMethods = {"testCreate", "testAddName", "testAddDescription", "testEditName", "testEditDescription"})
    public void testConfigureTheProject() {
        goConfigure();
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(Keys.LEFT_CONTROL + "a");
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(NAME);
        getDriver().findElement(By.name("_.description")).sendKeys(Keys.LEFT_CONTROL + "a");
        getDriver().findElement(By.name("_.description")).sendKeys(DESCRIPTION);

        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, 922);");

        final WebElement repositoryStrategy = getDriver().findElement(By.xpath(
                "//div[@class='tr jenkins-!-margin-bottom-2']/div/div/div/select"));
        Select selectRepositorySources = new Select(repositoryStrategy);
        selectRepositorySources.selectByValue("1");
        clickElement(By.xpath("//label[text()='Periodically if not otherwise run']"));
        clickElement(By.xpath("//label[text()='Discard old items']"));
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        clickElement(By.name("Submit"));

        Assert.assertEquals(textElement(By.tagName("h1")), NAME);
        Assert.assertEquals(textElement(By.id("view-message")), DESCRIPTION);
    }
}
