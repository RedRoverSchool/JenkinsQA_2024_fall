package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class DashboardItemsDropdownTest extends BaseTest {

    private WebDriverWait setWait() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        return wait;
    }

    @Test
    public void testNewItem() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement newItem = dropDownList.get(0);
        newItem.click();
    }

    @Test
    public void testBuildHistory() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement buildHistory = dropDownList.get(1);
        buildHistory.click();
    }

    @Test
    public void testManageJenkins() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        manageJenkins.click();
    }

    @Test
    public void testMngJenkinsSystem() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement system = mngJenkinsList.get(0);
        actions.moveToElement(system).perform();
        system.click();
    }

    @Test
    public void testMngJenkinsTools() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement tools = mngJenkinsList.get(1);
        actions.moveToElement(tools).perform();
        tools.click();
    }

    @Test
    public void testMngJenkinsPlugins() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement plugins = mngJenkinsList.get(2);
        actions.moveToElement(plugins).perform();
        plugins.click();
    }

    @Test
    public void testMngJenkinsNodes() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement nodes = mngJenkinsList.get(3);
        actions.moveToElement(nodes).perform();
        nodes.click();
    }

    @Test
    public void testMngJenkinsClouds() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement clouds = mngJenkinsList.get(4);
        actions.moveToElement(clouds).perform();
        clouds.click();
    }

    @Test
    public void testMngJenkinsAppearance() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement appearance = mngJenkinsList.get(5);
        actions.moveToElement(appearance).perform();
        appearance.click();
    }

    @Test
    public void testMngJenkinsSecurity() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement security = mngJenkinsList.get(6);
        actions.moveToElement(security).perform();
        security.click();
    }

    @Test
    public void testMngJenkinsCredentials() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement credentials = mngJenkinsList.get(7);
        actions.moveToElement(credentials).perform();
        credentials.click();
    }

    @Test
    public void testMngJenkinsProviderCreds() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement providerCreds = mngJenkinsList.get(8);
        actions.moveToElement(providerCreds).perform();
        providerCreds.click();
    }

    @Test
    public void testMngJenkinsUsers() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement users = mngJenkinsList.get(9);
        actions.moveToElement(users).perform();
        users.click();
    }

    @Test
    public void testMngJenkinsSystemInfo() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement systemInfo = mngJenkinsList.get(10);
        actions.moveToElement(systemInfo).perform();
        systemInfo.click();
    }

    @Test
    public void testMngJenkinsSystemLog() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement systemLog = mngJenkinsList.get(11);
        actions.moveToElement(systemLog).perform();
        systemLog.click();
    }

    @Test
    public void testMngJenkinsLoadStatistics() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement loadStatistics = mngJenkinsList.get(11);
        actions.moveToElement(loadStatistics).perform();
        loadStatistics.click();
    }

    @Test
    public void testMngJenkinsAboutJenkins() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement aboutJenkins = mngJenkinsList.get(12);
        actions.moveToElement(aboutJenkins).perform();
        aboutJenkins.click();
    }

    @Test
    public void testMngJenkinsMngOldData() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement mngOldData = mngJenkinsList.get(13);
        actions.moveToElement(mngOldData).perform();
        mngOldData.click();
    }

    @Test
    public void testMngJenkinsReloadConfig() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > button"));
        Assert.assertFalse(
                mngJenkinsList.isEmpty(), "Button 'Reload Configuration from Disk' is missing from list");

        WebElement reloadConfig = mngJenkinsList.get(0);
        reloadConfig.click();
    }

    @Test
    public void testMngJenkinsCLI() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement jenkinsCLI = mngJenkinsList.get(14);
        actions.moveToElement(jenkinsCLI).perform();
        jenkinsCLI.click();
    }

    @Test
    public void testMngJenkinsScript() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement script = mngJenkinsList.get(15);
        actions.moveToElement(script).perform();
        script.click();
    }

    @Test
    public void testMngJenkinsConsole() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement console = mngJenkinsList.get(16);
        actions.moveToElement(console).perform();
        console.click();

    }

    @Test
    public void testMngJenkinsShutDown() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement manageJenkins = dropDownList.get(2);
        actions.moveToElement(manageJenkins).perform();

        List<WebElement> mngJenkinsList = getDriver().findElements(
                By.cssSelector("#tippy-5 > div > div > div > a"));
        Assert.assertFalse(mngJenkinsList.isEmpty(), "Dropdown manage Jenkins - empty");

        WebElement prepareShutdown = mngJenkinsList.get(17);
        actions.moveToElement(prepareShutdown).perform();
        prepareShutdown.click();
    }

    @Test
    public void testMyViews() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement myViews = dropDownList.get(3);
        myViews.click();
    }
}

