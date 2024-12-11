package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.time.Duration;
import java.util.List;

public class OrganizationFolderTest extends BaseTest {
    private static final String FOLDER_NAME = "FolderName";
    private static final String DISPLAY_NAME = "DisplayName";
    private static final String DESCRIPTION = "Description";
    private static final String NEW_DISPLAY_NAME = "NewNameOrganizationFolder";
    private static final String NEW_DESCRIPTION = "NewDescription";

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

    private void scrollPage() {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    @Test
    public void testCreate() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .getHeader()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(projectList, FOLDER_NAME,
                "Project is not created");
    }

    @Test(dependsOnMethods = "testCreate")
    public void testAddDisplayName() {
        String displayName = new HomePage(getDriver())
                .openOrganisationFolderProject(FOLDER_NAME)
                .clickConfigure()
                .setDisplayName(DISPLAY_NAME)
                .clickSaveButton()
                .getName();

        Assert.assertEquals(displayName, DISPLAY_NAME);
    }

    @Test(dependsOnMethods = {"testAddDisplayName"})
    public void testEditDisplayName() {
        String newDisplayName = new HomePage(getDriver())
                .openOrganisationFolderProject(DISPLAY_NAME)
                .clickSidebarConfigButton()
                .editDisplayName(NEW_DISPLAY_NAME)
                .clickSaveButton()
                .getName();

        Assert.assertEquals(newDisplayName, NEW_DISPLAY_NAME);
    }

    @Test
    public void testAddDescription() {
        TestUtils.createOrganizationFolder(getDriver(), FOLDER_NAME);

        String description = new HomePage(getDriver())
                .openOrganisationFolderProject(FOLDER_NAME)
                .clickSidebarConfigButton()
                .enterDescription(DESCRIPTION)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Test(dependsOnMethods = {"testAddDescription"})
    public void testEditDescription() {
        String description = new HomePage(getDriver())
                .openOrganisationFolderProject(FOLDER_NAME)
                .clickSidebarConfigButton()
                .enterDescription(NEW_DESCRIPTION)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(description, NEW_DESCRIPTION);
    }

    @Test(dependsOnMethods = {"testEditDescription", "testEditDisplayName"})
    public void testDelete() {
        clickElement(By.xpath("//td/a[@class='jenkins-table__link model-link inside']"));
        clickElement(By.xpath("//a[@data-title='Delete Organization Folder']"));
        clickElement(By.xpath("//button[@data-id='ok']"));

        Assert.assertEquals(textElement(By.tagName("h1")), "Welcome to Jenkins!");
    }

    @Ignore
    @Test
    public void testConfigureTheProject() {
        goConfigure();
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(Keys.LEFT_CONTROL + "a");
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(DISPLAY_NAME);
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

        Assert.assertEquals(textElement(By.tagName("h1")), DISPLAY_NAME);
        Assert.assertEquals(textElement(By.id("view-message")), DESCRIPTION);
    }

    @Test
    public void testTitle() {

        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME)
                .selectOrganizationFolderAndClickOk();

        Assert.assertEquals(
                getDriver().findElement(By.id("general")).getText(),
                "General");
    }

    @Test
    public void testTooltipGeneralEnabled() {

        String tooltipText = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .getTooltipGeneralText();

        Assert.assertEquals(
                tooltipText,
                "(No new builds within this Organization Folder will be executed until it is re-enabled)");
    }

    @Test
    public void testDescriptionPreview() {

        String descriptionPreview = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .enterDisplayName(DISPLAY_NAME)
                .enterDescription(DESCRIPTION)
                .changeDescriptionPreviewState()
                .getDescriptionPreviewText();

        Assert.assertEquals(
                descriptionPreview,
                DESCRIPTION);
    }

    @Test
    public void testDescriptionPreviewHide() {
        String displayPreview = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .enterDisplayName(DISPLAY_NAME)
                .enterDescription(DESCRIPTION)
                .changeDescriptionPreviewState()
                .changeDescriptionPreviewState()
                .getPreviewStyleAttribute();

        Assert.assertEquals(
                displayPreview,
                "display: none;");
    }

    @Test
    public void testTooltipDelete() {

        Actions actions = new Actions(getDriver());

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(FOLDER_NAME);
        scrollPage();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button hetero-list-add']")).click();
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-dropdown__item '][2]")).click();
        actions.moveToElement(getDriver().findElement(By.xpath("//div/button[@title='Delete']"))).perform();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/button[@title='Delete']")).getAttribute("tooltip"),
                "Delete");
    }

    @Test
    public void testItemNameAfterCreation() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(FOLDER_NAME);
        scrollPage();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(DISPLAY_NAME);
        getDriver()
                .findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']"))
                .click();
        String itemNameExpected = "Folder name: " + FOLDER_NAME;

        WebDriverWait driverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main-panel")));

        String itemNameActual = getDriver().findElement(By.id("main-panel")).getText();
        int indexNameStart = itemNameActual.indexOf("\n");
        int indexNameEnd = itemNameActual.indexOf("\n", indexNameStart + 1);
        String itemNameActualCut = itemNameActual.substring(indexNameStart + 1, indexNameEnd);

        Assert.assertEquals(itemNameActualCut, itemNameExpected);
    }

    @Test

    public void testDescriptionAfterCreation() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(FOLDER_NAME);
        scrollPage();
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/textarea[@name='_.description']")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();

        WebDriverWait driverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message")));

        Assert.assertEquals(
                getDriver().findElement(By.id("view-message")).getText(),
                DESCRIPTION);
    }

    @Test
    public void testChangeItemName() {
        final String newName = FOLDER_NAME + "_New";

        String actualName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .openOrganisationFolderProject(FOLDER_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(newName)
                .clickRenameButton()
                .getName();

        Assert.assertEquals(actualName, newName);
    }

    @Test
    public void testCreateOrganizationFolderWithDefaultIcon() {
        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("Organization_Folder");

        scrollPage();

        getDriver().findElement(By.cssSelector("[class$='OrganizationFolder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        new Select(getDriver().findElement(By.xpath("(//select[contains(@class, 'dropdownList')])[2]")))
                .selectByVisibleText("Default Icon");

        scrollPage();

        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("h1 > svg")).getAttribute("title"), "Folder");
    }

    @Test
    public void testFolderDelete() {
        List<String> projectsList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .clickSaveButton()
                .getHeader()
                .gotoHomePage()
                .openOrganisationFolderProject(FOLDER_NAME)
                .clickDeleteButtonSidebarAndConfirm()
                .getItemList();

        Assert.assertListNotContainsObject(projectsList, FOLDER_NAME, "Project is not deleted");
    }
}
