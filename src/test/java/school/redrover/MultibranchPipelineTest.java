package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
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
                .clickNewItem()
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

    @Test(dependsOnMethods = "testVerifyErrorMessageWhenCreateWithSameName")
    public void testRenameMultibranchViaSideBar() {
        List<String> projectList = new HomePage(getDriver())
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME)
                .clickRenameSidebarButton()
                .clearInputFieldAndTypeName(MULTIBRANCH_PIPELINE_NAME2)
                .clickRenameButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME2,
                "Project is not renamed");
    }

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

    @Test(dependsOnMethods = "testRenameMultibranchViaSideBar")
    public void testCreateJobAndJobNameVisibleOnStatusPage() {
        String title = new HomePage(getDriver())
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME2)
                .getItemName();

        Assert.assertEquals(title, MULTIBRANCH_PIPELINE_NAME2);
    }

    @Test(dependsOnMethods = "testCreateJobAndJobNameVisibleOnStatusPage")
    public void testCreateJobAndJobNameVisibleOnBreadcrumb() {
        String breadcrumbName = new HomePage(getDriver())
                .openMultibranchPipelineProject(MULTIBRANCH_PIPELINE_NAME2)
                .getBreadcrumbName();

        Assert.assertEquals(breadcrumbName, MULTIBRANCH_PIPELINE_NAME2);
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
                .clickDeleteButtonSidebarAndConfirm()
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
                .selectDeleteFromItemMenuAndClickYes(MULTIBRANCH_PIPELINE_NAME)
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
                .openBreadcrumbDropdown()
                .clickDeleteBreadcrumbDropdownAndConfirm()
                .getItemList();

        Assert.assertListNotContainsObject(projectList, MULTIBRANCH_PIPELINE_NAME,
                "Project is not deleted");
    }
}