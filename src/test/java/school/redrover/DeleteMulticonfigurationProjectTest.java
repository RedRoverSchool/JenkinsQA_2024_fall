package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.MyViewsPage;
import school.redrover.runner.BaseTest;

public class DeleteMulticonfigurationProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "MulticonfigurationProject";

    private HomePage createMulticonfigurationProject(String projectName) {
        return new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectMultiConfigurationAndClickOk()
                .gotoHomePage();
    }

    @Test
    public void testDeletionPopupAppearsOnProjectPage() {
        WebElement deletionPopup = createMulticonfigurationProject(PROJECT_NAME)
                .openMultiConfigurationProject(PROJECT_NAME)
                .clickDeleteProject()
                .getDeletionPopup();

        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test(dependsOnMethods = "testDeletionPopupAppearsOnProjectPage")
    public void testDeletionPopupAppearsOnMainPage() {
        WebElement deletionPopup = new HomePage(getDriver())
                .openDropdownViaChevron(PROJECT_NAME)
                .clickDeleteInProjectDropdown(PROJECT_NAME)
                .getDeletionPopup();

        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test(dependsOnMethods = "testDeletionPopupAppearsOnMainPage")
    public void testDeletionPopupAppearsOnMyViews() {
        WebElement deletionPopup = new HomePage(getDriver())
                .goToMyViews()
                .openDropdownViaChevron(PROJECT_NAME)
                .clickDeleteInProjectDropdown(PROJECT_NAME)
                .getDeletionPopup();

        Assert.assertTrue(deletionPopup.isDisplayed());
    }

    @Test
    public void testDeleteProjectFromProjectPage() {
        createMulticonfigurationProject(PROJECT_NAME).openMultiConfigurationProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//*[contains(@class,'icon-edit-delete')]")).click();

        WebElement deletionPopup = getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//button[normalize-space()='Yes']"))));
        deletionPopup.click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[.='Welcome to Jenkins!']")).getText(),
                "Welcome to Jenkins!");

    }

    @Test
    public void testDeleteViaDropDownMenu() {
        createMulticonfigurationProject(PROJECT_NAME);

        new HomePage(getDriver()).openDropdownViaChevron(PROJECT_NAME);

        getDriver().findElement(By.xpath("//button[@href='/job/%s/doDelete']".formatted(PROJECT_NAME))).click();

        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertEquals(getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1"))).getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testDeleteFromMyViewsPage() {
        createMulticonfigurationProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//a[@href='/me/my-views']")).click();

        new MyViewsPage(getDriver()).deleteAnyJobViaChevron(PROJECT_NAME);

        Assert.assertTrue(getDriver().getPageSource().contains("This folder is empty"));
    }
}
