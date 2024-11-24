package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.CreateNewItemPage;
import school.redrover.page.ProjectPage;
import school.redrover.runner.BaseTest;

public class DeleteMulticonfigurationProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MulticonfigurationProject";

    private HomePage createProject(String projectName) {
        HomePage homePage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectProjectTypeAndSave(CreateNewItemPage.ItemType.MULTICONFIGURATION_PROJECT)
                .goToDashboard();
        return homePage;
    }

    @Test
    public void testPopupForDeletionOnProjectPage() {
        createProject(PROJECT_NAME).openProject(PROJECT_NAME);

        getDriver().findElement(
                By.xpath("//div[@id='side-panel']//span[text()='Delete Multi-configuration project']")).click();

        WebElement deletionPopup = getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//footer/following-sibling::dialog"))));

        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test(dependsOnMethods = "testPopupForDeletionOnProjectPage")
    public void testPopupForDeletionOnMainPage() {
        new HomePage(getDriver()).openDropdownViaChevron(PROJECT_NAME);

        getDriver().findElement(By.xpath("//button[@href='/job/%s/doDelete']".formatted(PROJECT_NAME))).click();

        WebElement deletionPopup = getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//footer/following-sibling::dialog"))));
        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test(dependsOnMethods = "testPopupForDeletionOnProjectPage")
    public void testPopupForDeletionOnMyViews() {
        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();

        new HomePage(getDriver()).openDropdownViaChevron(PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@href='/me/my-views/view/all/job/%s/doDelete']".formatted(PROJECT_NAME))).click();

        WebElement deletionPopup = getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//footer/following-sibling::dialog"))));
        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test
    public void testDeleteViaDropDownMenu() {
        createProject(PROJECT_NAME);

        new HomePage(getDriver()).openDropdownViaChevron(PROJECT_NAME);

        getDriver().findElement(By.xpath("//button[@href='/job/%s/doDelete']".formatted(PROJECT_NAME))).click();

        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertTrue(getDriver().getPageSource().contains("Welcome to Jenkins"));


    }
}
