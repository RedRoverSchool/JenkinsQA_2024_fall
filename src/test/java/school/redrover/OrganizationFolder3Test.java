package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class OrganizationFolder3Test extends BaseTest {

    private static final String NAME_FOLDER = "Organization Folder";
    private static final String NAME = "Name Organization Folder";
    private static final String NEW_NAME = "New Name Organization Folder";
    private static final String DESCRIPTION = "Description Organization Folder";
    private static final String NEW_DESCRIPTION = "New Description Organization Folder";

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
        String typeProject = new HomePage(getDriver())
                .clickCreateJob()
                .enterItemName(NAME_FOLDER)
                .selectOrganizationFolderAndClickOk()
                .gotoHomePage()
                .getTypeProject();

        Assert.assertEquals(typeProject, NAME_FOLDER);
    }

//    @Test(dependsOnMethods = "testCreate")
//    public void testAddName() {
//        String name = new HomePage(getDriver())
//                .clickItemName()
//                .clickConfigure()
//                .setDisplayName(NAME)
//                .clickSaveButton()
//                .getName();
//
//        Assert.assertEquals(name, NAME);
//    }

    @Test(dependsOnMethods = {"testAddName"})
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

    @Test(dependsOnMethods = {"testAddDescription"})
    public void testEditDescription() {
        goConfigure();
        getDriver().findElement(By.name("_.description")).sendKeys(Keys.LEFT_CONTROL + "a");
        getDriver().findElement(By.name("_.description")).sendKeys(NEW_DESCRIPTION);
        clickElement(By.name("Submit"));

        Assert.assertEquals(textElement(By.id("view-message")), NEW_DESCRIPTION);
    }

    @Test(dependsOnMethods = {"testEditDescription", "testEditName"})
    public void testDelete() {
        clickElement(By.xpath("//td/a[@class='jenkins-table__link model-link inside']"));
        clickElement(By.xpath("//a[@data-title='Delete Organization Folder']"));
        clickElement(By.xpath("//button[@data-id='ok']"));

        Assert.assertEquals(textElement(By.tagName("h1")), "Welcome to Jenkins!");
    }

    @Ignore
    @Test(dependsOnMethods = {"testAddName", "testAddDescription", "testEditName", "testEditDescription"})
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
