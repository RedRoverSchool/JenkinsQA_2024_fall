package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MULTIBRANCH_PIPELINE_NAME = "MultibranchName";
    private static final String MULTIBRANCH_PIPELINE_NAME2 = "NewMultibranchName";

    private void createJob(String jobName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.name("name")).sendKeys(jobName);
        getDriver().findElement(By.xpath("//li[contains(@class,'MultiBranchProject')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testCreate() {
        List<String> projectList = new HomePage(getDriver())
                .clickCreateJob()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME,
                "Project is not created");
    }

    @Test
    public void testAddDescriptionCreatingProject() {
        final String description = "AddedDescription";

        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .enterDescription(description)
                .clickSaveButton()
                .getDescription();

        Assert.assertEquals(actualDescription, description);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testVerifyErrorMessageWhenCreateWithSameName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(MULTIBRANCH_PIPELINE_NAME));
    }

    @Test
    public void testVerifyErrorMessageWhenCreateWithDot() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(".")
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» “.” is not an allowed name");
    }

    @Test
    public void testVerifyErrorMessageWhenCreateWithEmptyName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectMultibranchPipelineProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
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
    public void testSelectingTriggersScanPeriodFromConfigPage() {
        WebElement selectedValue = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickScanMultibranchPipelineButton()
                .clickPeriodicalScanningCheckbox()
                .selectingIntervalValue()
                .getSelectedValue();

        Assert.assertTrue(selectedValue.isSelected());
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
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME)
                .deleteItemBySidebar()
                .getItemList();

        Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME,
                "Project is not deleted");
    }

    @Test
    public void testDeleteJobUsingItemDropdownOnDashboard() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .deleteItemViaChevronItem(MULTIBRANCH_PIPELINE_NAME)
                .getItemList();

        Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME,
                "Project is not deleted");
    }

    @Test
    public void testDeleteJobUsingDropdownBreadcrumbJobPage() {
        List<String> projectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(MULTIBRANCH_PIPELINE_NAME)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME)
                .deleteJobUsingDropdownBreadcrumbJobPage()
                .getItemList();

        Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME,
                "Project is not deleted");
    }
}