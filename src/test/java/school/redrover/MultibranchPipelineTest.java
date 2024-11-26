package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MP_NAME = "NewItem";
    private static final By NAME_INPUT = By.id("name");
    private static final By CREATE_A_JOB_BUTTON = By.cssSelector("[href='newJob']");
    private static final By MULTIBRANCH_PIPELINE_PROJECT = By.cssSelector("[class$='MultiBranchProject']");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final String MULTIBRANCH_PIPELINE_NAME = "NewMultibranchName";
    private static final String MULTIBRANCH_PIPELINE_NAME2 = "NewMultibranchName2";

    private void createJob(String jobName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.name("name")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//li[contains(@class,'MultiBranchProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void scrollDown() {
        JavascriptExecutor scroll = (JavascriptExecutor) getDriver();
        scroll.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    @Test
    public void testAddDescriptionCreatingMultibranch() {
        final String description = "AddedDescription";

        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MP_NAME)
                .selectMultibranchPipelineAndClickOk()
                .enterDescription(description)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, description);
    }

    @Test
    public void testDeleteItemFromStatusPage() {

        createJob(MP_NAME);

        getDriver().findElement(By.xpath(("//a[contains(@href,'%s')]").formatted(MP_NAME))).click();

        getDriver().findElement(By.cssSelector("[data-message*='Delete the Multibranch Pipeline']")).click();

        getDriver().findElement(By.cssSelector("[data-id='ok']")).click();

        String dashboardText = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(dashboardText, "Welcome to Jenkins!");
    }

    @Test
    public void testRenameMultibranchViaSideBar() {

        getDriver().findElement(By.cssSelector("[href='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("Hilton");
        getDriver().findElement(By.cssSelector("[class*='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[7]")).click();

        getDriver().findElement(By.cssSelector("[class*='input validated']")).clear();
        getDriver().findElement(By.cssSelector("[class*='input validated']")).sendKeys("Hilton Hotels");
        getDriver().findElement(By.cssSelector("[class*='submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Hilton Hotels");
    }

    @Ignore
    @Test
    public void testTryCreateProjectExistName() {
        final String projectName = "MultiBuild";
        final String errorMessage = "» A job already exists with the name " + "‘" + projectName + "’";

        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);

        scrollDown();

        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        scrollDown();

        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins-home-link']")).click();

        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();

        scrollDown();

        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);

        String actualMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//*[@id='itemname-invalid']"))).getText();

        Assert.assertEquals(actualMessage, errorMessage);
    }

    @Ignore
    @Test
    public void testSelectingTriggersScanPeriodFromConfigPage() throws InterruptedException {

        getDriver().findElement(CREATE_A_JOB_BUTTON).click();

        getDriver().findElement(NAME_INPUT).sendKeys(MP_NAME);
        getDriver().findElement(MULTIBRANCH_PIPELINE_PROJECT).click();
        getDriver().findElement(OK_BUTTON).click();

        getDriver().findElement(By.cssSelector("[data-section-id='scan-multibranch-pipeline-triggers']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.cssSelector("[class='jenkins-checkbox']")).click();
        Select select = new Select(getDriver().findElement(By.cssSelector("[name*='interval']")));
        select.selectByValue("12h");

        WebElement selectedValue = getDriver().findElement(By.cssSelector("[value='12h']"));
        Assert.assertTrue(selectedValue.isSelected());
    }

    @Test
    public void testCreateOneJobAndDisplayOnStartPage() {
        String actualJobName = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .goToDashboard()
                .getItemName();

        Assert.assertEquals(actualJobName, MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testCreateJobAndDisplayAmongOtherJobsOnStartPage() {
        createJob(MULTIBRANCH_PIPELINE_NAME);
        createJob(MULTIBRANCH_PIPELINE_NAME2);

        List<WebElement> jobs = getDriver().findElements(By.xpath("//a[@class='jenkins-table__link model-link inside']"));
        List<String> jobNames = jobs.stream().map(WebElement::getText).toList();

        Assert.assertListContainsObject(jobNames, MULTIBRANCH_PIPELINE_NAME2, MULTIBRANCH_PIPELINE_NAME2);
    }

    @Test
    public void testEnterInvalidNameAndSeesAppropriateMessages() {
        List<String> invalidSymbols = List.of("!", "@", "#", "$", "%", "^", "&", "*", "|", "/", "?", ":", ";", "\\");

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        for (int i = 0; i < 14; i++) {
            getDriver().findElement(By.name("name")).sendKeys(invalidSymbols.get(i));

            Assert.assertEquals(
                    getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText(),
                    ("» ‘%s’ is an unsafe character").formatted(invalidSymbols.get(i)));
            getDriver().findElement(By.name("name")).sendKeys(Keys.BACK_SPACE);
        }
    }

    @Test
    public void testEnterInvalidNameDotAndSeesAppropriateMessages() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.name("name")).sendKeys(".");

        Assert.assertEquals(
                getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText(),
                "» “.” is not an allowed name");
    }

    @Test
    public void testEnterInvalidNameEmptyAndSeesAppropriateMessages() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//li[contains(@class,'MultiBranchProject')]")).click();

        Assert.assertEquals(
                getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-required"))).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateJobAndJobNameVisibleOnStatusPage() {
        createJob(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.xpath(("//a[contains(@href,'%s')]").formatted(MULTIBRANCH_PIPELINE_NAME))).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//h1")).getText(),
                MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testCreateJobAndJobNameVisibleOnBreadcrumb() {
        createJob(MULTIBRANCH_PIPELINE_NAME);

        getDriver().findElement(By.xpath(("//a[contains(@href,'%s')]").formatted(MULTIBRANCH_PIPELINE_NAME))).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//a[contains(@href,'job')][@class='model-link']")).getText(),
                MULTIBRANCH_PIPELINE_NAME);
    }

    @Test
    public void testDeleteJobUsingSidebarStatusPage() {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .goToDashboard()
                .clickOnCreatedItem(MULTIBRANCH_PIPELINE_NAME)
                .deleteItemBySidebar();

        Assert.assertTrue(getDriver().findElements(
                By.xpath("//span[text()='%s']".formatted(MULTIBRANCH_PIPELINE_NAME))).isEmpty());
    }

    @Test
    public void testDeleteJobUsingItemDropdownOnDashboard() {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .goToDashboard()
                .deleteItemViaChevronItem(MULTIBRANCH_PIPELINE_NAME);

        Assert.assertTrue(getDriver().findElements(
                By.xpath("//span[text()='%s']".formatted(MULTIBRANCH_PIPELINE_NAME))).isEmpty());
    }

    @Test
    public void testDeleteJobUsingDropdownBreadcrumbJobPage() {
        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .goToDashboard()
                .clickOnCreatedItem(MULTIBRANCH_PIPELINE_NAME)
                .deleteJobUsingDropdownBreadcrumbJobPage();

        Assert.assertTrue(getDriver().findElements(
                By.xpath("//span[text()='%s']".formatted(MULTIBRANCH_PIPELINE_NAME))).isEmpty());
    }
}