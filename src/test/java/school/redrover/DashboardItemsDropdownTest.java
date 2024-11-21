package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;
import java.util.List;

public class DashboardItemsDropdownTest extends BaseTest {

    @Test
    public void testNewItem() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement newItem = listDD.get(0);
        actions.moveToElement(newItem).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/view/all/newJob$"), "Page is invalid");
    }

    @Test
    public void testBuildHistory() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement buildHistory = listDD.get(1);
        actions.moveToElement(buildHistory).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/view/all/builds$"), "Page is invalid");
    }

    @Test
    public void testManageJenkins() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsSystem() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement system = mngJenkinsList.get(0);
        actions.moveToElement(system).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/configure$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsTools() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement tools = mngJenkinsList.get(1);
        actions.moveToElement(tools).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/configureTools$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsPlugins() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement plugins = mngJenkinsList.get(2);
        actions.moveToElement(plugins).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/pluginManager$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsNodes() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement nodes = mngJenkinsList.get(3);
        actions.moveToElement(nodes).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/computer$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsClouds() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement clouds = mngJenkinsList.get(4);
        actions.moveToElement(clouds).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/cloud$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsAppearance() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement appearance = mngJenkinsList.get(5);
        actions.moveToElement(appearance).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/appearance$"), "Page is invalid");
    }

    @Test
    public void testMyViews() {
    Actions actions = new Actions(getDriver());

    WebElement dashboardButton = getDriver().findElement(
            By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

    WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

    List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
            By.cssSelector("#tippy-3 > div > div > div > a")));

    WebElement myViews = listDD.get(3);
        actions.moveToElement(myViews).click().perform();

    String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/my-views/view/all(/)?$"), "Page is invalid");
}

}
