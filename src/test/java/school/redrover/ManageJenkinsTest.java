package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    private void openManageJenkinsPage() {

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
    }

    @Ignore
    @Test
    public void testManageJenkinsTab() {

        List<WebElement> tasks = getDriver().findElements(
            By.xpath("//div[@id='tasks']//a"));
        Assert.assertEquals(tasks.size(), 1);

        List<String> expectedTexts = Arrays.asList("New Item", "Build History", "Manage Jenkins", "My Views");
        for (int i = 0; i < tasks.size() && i < expectedTexts.size(); i++) {
            Assert.assertEquals(tasks.get(i).getText(), expectedTexts.get(i));
        }

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement manageJenkinsTask = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Manage Jenkins']]")));
        manageJenkinsTask.click();

    }

    @Test
    public void testManageJenkinsTabSections() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement manageJenkinsTab = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Manage Jenkins']]"))
        );
        manageJenkinsTab.click();

        List<WebElement> sections = getDriver().findElements(
            By.xpath("//h2[@class='jenkins-section__title']"));
        Assert.assertEquals(sections.size(), 5);

        List<String> expectedSectionTitles = Arrays.asList(
            "System Configuration", "Security", "Status Information", "Troubleshooting", "Tools and Actions"
        );
        for (int i = 0; i < expectedSectionTitles.size(); i++) {
            Assert.assertEquals(sections.get(i).getText(), expectedSectionTitles.get(i));

        }
    }

    @Test
    public void testManageJenkinsSections() {
        final List<String> expectedSectionsTitles = List.of("System Configuration", "Security", "Status Information",
            "Troubleshooting", "Tools and Actions");

        openManageJenkinsPage();

        List<WebElement> sections = getDriver().findElements(By.xpath("//h2[@class='jenkins-section__title']"));
        List<String> actualSectionsTitles = sections.stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualSectionsTitles, expectedSectionsTitles);
    }

    @Test
    public void testManageJenkinsSystemConfigurationItems() {
        final List<String> expectedItemsNames = List.of("System", "Tools", "Plugins",
            "Nodes", "Clouds", "Appearance");

        openManageJenkinsPage();

        List<WebElement> systemConfigItems = getDriver()
                                                 .findElements(By.xpath("(//div[@class='jenkins-section__items'])[1]//dt"));
        List<String> actualItemsNames = systemConfigItems.stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualItemsNames, expectedItemsNames);
    }

    @Test
    public void testManageJenkinsSystemConfigureBreadcrumbs() {
        final String expectedBreadCrumbs = "Dashboard\nManage Jenkins\nSystem";

        openManageJenkinsPage();

        getDriver().findElement(By.cssSelector("a[href='configure']")).click();

        String actualBreadCrumbs = getDriver().findElement(By.id("breadcrumbs")).getText();

        Assert.assertEquals(actualBreadCrumbs, expectedBreadCrumbs);
    }

    @Test
    public void testManageJenkinsTabSearch() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement manageJenkinsTab = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Manage Jenkins']]"))
        );
        manageJenkinsTab.click();

        WebElement searchField = getDriver().findElement(
            By.xpath("//input[@id='settings-search-bar']"));
        Assert.assertEquals(searchField.getAttribute("placeholder"), "Search settings");

    }

    @Test
    public void testSearchSystemConfigurationItems() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement manageJenkinsTab = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Manage Jenkins']]")));
        manageJenkinsTab.click();

        WebElement searchField = getDriver().findElement(
            By.xpath("//input[@id='settings-search-bar']"));

        List<String> itemsSystemConfiguration = Arrays.asList(
            "System", "Tools", "Plugins", "Nodes", "Clouds", "Appearance");
        for (String itemsSystemConfigurations : itemsSystemConfiguration) {
            wait.until(ExpectedConditions.elementToBeClickable(searchField)).clear();
            wait.until(ExpectedConditions.visibilityOf(searchField)).sendKeys(itemsSystemConfigurations);

            WebElement searchDropdown = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='jenkins-search__results']")));

            Assert.assertNotNull(searchDropdown, "Item '" + itemsSystemConfigurations + "' not found in dropdown.");

        }
    }

    @Test
    public void testSearchSettingsWithNoResult() {

        openManageJenkinsPage();

        getDriver().findElement(By.cssSelector("input[id=settings-search-bar]")).sendKeys("1333");

        WebElement noResultLabel = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class$='no-results-label']")));

        String actualNoResultText = noResultLabel.getText();

        Assert.assertEquals(actualNoResultText, "No results");
    }

    @Test
    public void testRedirectToFirstItemAfterPressEnter() {

        openManageJenkinsPage();

        WebElement searchSettingInput = getDriver().findElement(By.cssSelector("input[id=settings-search-bar]"));

        searchSettingInput.sendKeys("c");

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-search__results-item--selected")));

        searchSettingInput.sendKeys(Keys.ENTER);

        String actualURL = getDriver().getCurrentUrl();

        Assert.assertEquals(actualURL, ProjectUtils.getUrl() + "manage/cloud/");
    }

    @Test
    public void testSearchSettingTooltip() {

        openManageJenkinsPage();

        WebElement tooltip = getDriver().findElement(By.cssSelector("[class='jenkins-search__shortcut']"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(tooltip).perform();

        WebElement tooltipHiddenAttribute = getWait5().until(ExpectedConditions
                                                           .visibilityOfElementLocated(By.xpath("//div[@aria-describedby='tippy-6']")));

        Assert.assertTrue(tooltipHiddenAttribute.isDisplayed());
    }

    @Test
    public void testShortcutMovesFocusToSearchSettingsField() {
        final String expectedTextInSearchSettingsField = "123456";

        openManageJenkinsPage();

        Actions actions = new Actions(getDriver());
        actions.sendKeys(Keys.chord("/", expectedTextInSearchSettingsField)).perform();

        String actualTextInSearchSettingsField = getDriver()
                                                     .findElement(By.cssSelector("input[id='settings-search-bar']"))
                                                     .getAttribute("value");

        Assert.assertEquals(actualTextInSearchSettingsField, expectedTextInSearchSettingsField);
    }

    @Test
    public void testSearchSettingsFieldIsDisplayed() {

        openManageJenkinsPage();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("input[id='settings-search-bar']")).isDisplayed());
    }

    @Test
    public void testSearchResultsList() {
        final List<String> expectedSearchResults = List.of("System", "System Information", "System Log");

        openManageJenkinsPage();

        getDriver().findElement(By.cssSelector("input[id='settings-search-bar']")).sendKeys("sy");

        List<WebElement> searchResultElements = getWait5()
                                                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                                        By.xpath("//div[@class='jenkins-search__results']//a")));

        List<String> actualSearchResults = searchResultElements.stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualSearchResults, expectedSearchResults);
    }

    @Test
    public void testRedirectToSelectedItemFromResultList() {

        openManageJenkinsPage();

        getDriver().findElement(By.cssSelector("input[id='settings-search-bar']")).sendKeys("cr");

        WebElement configureCredentialsItem = getWait5().until(ExpectedConditions
                             .visibilityOfElementLocated(By.cssSelector("a[href$='configureCredentials']")));
        configureCredentialsItem.click();

        Assert.assertTrue(getDriver().getCurrentUrl().endsWith("/manage/configureCredentials/"));
    }
}