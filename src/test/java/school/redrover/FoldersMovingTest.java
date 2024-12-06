package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

import static school.redrover.runner.TestUtils.newItemsData;

public class FoldersMovingTest extends BaseTest {

    private static final String FOLDER_BUTTON_XPATH = "//*[@id='j-add-item-type-nested-projects']/ul/li[1]/div[2]/div";

    private void clickOnSave() {
        getDriver().findElement(By.name("Submit")).click();
    }

    private String getFolderExtension() {
        return getDriver().findElement(By.id("breadcrumbBar")).getText();
    }

    private void setDestinationFolder(String selectPath) {
        WebElement folderDestination = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("destination")));
        Select select = new Select(folderDestination);
        select.selectByVisibleText(selectPath);
        getDriver().findElement(By.name("Submit")).click();
    }

    private void clickOnMove() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div[6]/span/a"))).click();
    }

    private void goToTheMainPage() {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='breadcrumbs']/li[1]/a"))).click();
    }

    private void useChevron() {
        WebElement folder = getDriver().findElement(
                By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[3]/a/span"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(folder).perform();

        WebElement chooseAction = getWait10().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[3]/a/button")));
        actions.moveToElement(chooseAction).pause(500).click().perform();

        TestUtils.moveAndClickWithJavaScript(getDriver(), chooseAction);

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div/div/div/a[4]"))).click();
        setDestinationFolder("Jenkins » FolderTwo");
    }

    private void goToMyView() {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div[4]/span/a"))).click();
    }

    private void goThroughFoldersOnLowerLevel() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/table/tbody/tr/td[3]/a/span"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/div[3]/div[2]/table/tbody/tr[1]/td[3]/a/span"))).click();
    }

    @Test
    public void testMoveFromFoldersPage() {

        List<String> nameProjectList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("FolderParent")
                .selectFolderAndClickOk()
                .gotoHomePage()

                .clickNewItem()
                .enterItemName("FolderChild")
                .selectFolderAndClickOk()
                .gotoHomePage()

                .openFolder("FolderChild")
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove("FolderParent")
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectList, List.of("Dashboard", "FolderParent", "FolderChild"));
    }

    @Test(dependsOnMethods = "testMoveFromFoldersPage")
    public void testMoveFromTheSameLevel() {
        List<String> nameProjectsList = new HomePage(getDriver())
                .openFolder("FolderParent")
                .clickNewItem()
                .enterItemName("FolderChild2")
                .selectFolderAndClickOk()
                .clickSaveButton()
                .gotoHomePage()
                .openFolder("FolderParent")
                .openFolder("FolderChild2")
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove("FolderParent/FolderChild")
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectsList, List.of("Dashboard", "FolderParent", "FolderChild", "FolderChild2"));
    }

    @Test(dependsOnMethods = "testMoveFromTheSameLevel")
    public void testTryToMoveInTheSamePlace () {
        List<String> nameProjectsListBreadcrumbs = new HomePage(getDriver())
                .openFolder("FolderParent")
                .openFolder("FolderChild")
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove("FolderParent")
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectsListBreadcrumbs, List.of("Dashboard", "FolderParent", "FolderChild", "Move"));
    }

    @Test(dependsOnMethods = "testMoveFromTheSameLevel")
    public void testMoveOnTheHigherLevel() {
        List<String> nameProjectsList = new HomePage(getDriver())
                .openFolder("FolderParent")
                .openFolder("FolderChild")
                .openFolder("FolderChild2")
                .clickMoveOnSidebar()
                .selectParentFolderAndClickMove("FolderParent")
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectsList, List.of("Dashboard", "FolderParent", "FolderChild2"));
    }

    @Test(dependsOnMethods = "testMoveOnTheHigherLevel")
    public void testNoOptionsToMoveParentIntoChild () {
        List<String> itemsSidebar = new HomePage(getDriver())
                .openFolder("FolderParent")
                .getListOfItemsSidebar();

        Assert.assertListNotContains(itemsSidebar, item->item.contains("Move"), "list of sidebar items contains Move");
    }

    @Test
    public void testMoveViaChevronMainPage () {

       List<String> nameProjectsList =  new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("FolderParent")
                .selectFolderAndClickOk()
                .gotoHomePage()

                .clickNewItem()
                .enterItemName("FolderChild")
                .selectFolderAndClickOk()
                .gotoHomePage()

                .selectMoveFromItemMenuByChevron("FolderChild")
                .selectParentFolderAndClickMove("FolderParent")
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectsList, List.of("Dashboard", "FolderParent", "FolderChild"));
    }

    @Test
    public void testMoveViaChevronMyView () {

        List<String> nameProjectsList =  new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("FolderParent")
                .selectFolderAndClickOk()
                .gotoHomePage()

                .clickNewItem()
                .enterItemName("FolderChild")
                .selectFolderAndClickOk()
                .gotoHomePage()

                .clickMyViewsButton()
                .selectMoveFromItemMenuByChevron("FolderChild")
                .selectParentFolderAndClickMove("FolderParent")
                .getBreadcrumsBarItemsList();

        Assert.assertEquals(nameProjectsList, List.of("Dashboard", "FolderParent", "FolderChild"));
    }

}

