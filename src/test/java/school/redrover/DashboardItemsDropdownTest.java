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

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement newItem = listDD.get(0);
        actions.moveToElement(newItem).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/view/all/newJob$"), "Page is invalid");
    }

    @Test
    public void testBuildHistory() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement buildHistory = listDD.get(1);
        actions.moveToElement(buildHistory).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/view/all/builds$"), "Page is invalid");
    }

    @Test
    public void testManageJenkins() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsSystem() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement system = mngJenkinsList.get(0);
        actions.moveToElement(system).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/configure(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsTools() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement tools = mngJenkinsList.get(1);
        actions.moveToElement(tools).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/configureTools(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsPlugins() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement plugins = mngJenkinsList.get(2);
        actions.moveToElement(plugins).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/pluginManager(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsNodes() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement nodes = mngJenkinsList.get(3);
        actions.moveToElement(nodes).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/computer(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsClouds() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement clouds = mngJenkinsList.get(4);
        actions.moveToElement(clouds).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/cloud(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsAppearance() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement appearance = mngJenkinsList.get(5);
        actions.moveToElement(appearance).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/appearance(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsSecurity() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement security = mngJenkinsList.get(6);
        actions.moveToElement(security).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/configureSecurity(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsCredentials() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement credentials = mngJenkinsList.get(7);
        actions.moveToElement(credentials).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/credentials(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsProviderCreds() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement providerCreds = mngJenkinsList.get(8);
        actions.moveToElement(providerCreds).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/configureCredentials(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsUsers() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement users = mngJenkinsList.get(9);
        actions.moveToElement(users).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/securityRealm(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsSystemInfo() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement systemInfo = mngJenkinsList.get(10);
        actions.moveToElement(systemInfo).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/systemInfo(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsSystemLog() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement systemLog = mngJenkinsList.get(11);
        actions.moveToElement(systemLog).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/log(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsLoadStatistics() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement loadStatistics = mngJenkinsList.get(12);
        actions.moveToElement(loadStatistics).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/load-statistics(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsAboutJenkins() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement aboutJenkins = mngJenkinsList.get(13);
        actions.moveToElement(aboutJenkins).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/about(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsMngOldData() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > a"));
        WebElement mngOldData = mngJenkinsList.get(14);
        actions.moveToElement(mngOldData).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/administrativeMonitor/OldData/manage(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsCLI() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > button"));
        WebElement jenkinsCLI = mngJenkinsList.get(15);
        actions.moveToElement(jenkinsCLI).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/cli(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsScript() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > button"));
        WebElement script = mngJenkinsList.get(16);
        actions.moveToElement(script).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/cli(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsConsole() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > button"));
        WebElement console = mngJenkinsList.get(17);
        actions.moveToElement(console).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/script(/)?$"), "Page is invalid");
    }

    @Test
    public void testMngJenkinsShutDown() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement mngJenkins = listDD.get(2);
        actions.moveToElement(mngJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(By.cssSelector("#tippy-6 > div > div > div > button"));
        WebElement prepareShutdown = mngJenkinsList.get(17);
        actions.moveToElement(prepareShutdown).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/manage/prepareShutdown(/)?$"), "Page is invalid");
    }

    @Test
    public void testMyViews() {
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        TestUtils.moveAndClickWithJavaScript(getDriver(), buttonDropdown);

        List<WebElement> listDD = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement myViews = listDD.get(3);
        actions.moveToElement(myViews).click().perform();

        String actualUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(actualUrl.matches(".*\\/my-views/view/all(/)?$"), "Page is invalid");
    }
}
