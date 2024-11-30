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
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ManageJenkinsTest extends BaseTest {

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
        List<String> mainSections = new HomePage(getDriver())
                                .openManageJenkinsPage()
                                .getAllSections();

        Assert.assertEquals(mainSections, List.of("System Configuration", "Security", "Status Information",
            "Troubleshooting", "Tools and Actions"));
    }

    @Test
    public void testManageJenkinsSystemConfigurationItems() {
        List <String> itemsNames = new HomePage(getDriver())
                                       .openManageJenkinsPage()
                                       .getSystemConfigurationItems();

        Assert.assertEquals(itemsNames, List.of("System", "Tools", "Plugins",
            "Nodes", "Clouds", "Appearance"));
    }

    @Test
    public void testManageJenkinsSystemConfigureBreadcrumbs() {
        String breadCrumbs = new HomePage(getDriver())
                                 .openManageJenkinsPage()
                                 .clickSystemSection()
                                 .getBreadCrumbs();

        Assert.assertEquals(breadCrumbs, "Dashboard\nManage Jenkins\nSystem");
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
        String resultLabelText = new HomePage(getDriver())
                                     .openManageJenkinsPage()
                                     .typeSearchInputField("1333")
                                     .getNoResultLabelText();

        Assert.assertEquals(resultLabelText, "No results");
    }

    @Test
    public void testRedirectToFirstItemAfterPressEnter() {

        String currentUrl = new HomePage(getDriver())
                                .openManageJenkinsPage()
                                .pressEnterAfterInput("c")
                                .getCurrentUrl();

        Assert.assertEquals(currentUrl, ProjectUtils.getUrl() + "manage/cloud/");
    }

    @Test
    public void testSearchSettingTooltip() {

        boolean shortcutTooltipIsDisplayed = new HomePage(getDriver())
                                         .openManageJenkinsPage()
                                         .isHiddenShortcutTooltipDisplayed();

        Assert.assertTrue(shortcutTooltipIsDisplayed);
    }

    @Test
    public void testShortcutMovesFocusToSearchSettingsField() {
        final String expectedText = "123456";

        String inputText = new HomePage(getDriver())
                               .openManageJenkinsPage()
                               .switchFocusToSearchFieldAndTypeText(expectedText)
                               .getInputText();

        Assert.assertEquals(inputText, expectedText);
    }

    @Test
    public void testSearchSettingsFieldIsDisplayed() {
        boolean searchSettingFieldIsDisplayed = new HomePage(getDriver())
                                                    .openManageJenkinsPage()
                                                    .isSearchSettingFieldDisplayed();

        Assert.assertTrue(searchSettingFieldIsDisplayed);
    }

    @Test
    public void testSearchResultsList() {
        List<String> searchResults = new HomePage(getDriver())
                                         .openManageJenkinsPage()
                                         .typeSearchInputField("sy")
                                         .getSearchResults();

        Assert.assertEquals(searchResults, List.of("System", "System Information", "System Log"));
    }

    @Test
    public void testRedirectToSelectedItemFromResultList() {
        String currentUrl = new HomePage(getDriver())
                                .openManageJenkinsPage()
                                .typeSearchInputField("cr")
                                .clickConfigureCredentialsItem()
                                .getCurrentUrl();

        Assert.assertTrue(currentUrl.endsWith("/manage/configureCredentials/"));
    }
}