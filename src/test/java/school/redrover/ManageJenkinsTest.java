package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    @Test
    public void testCheckTitle() {
        String title = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .getTitle();

        Assert.assertTrue(title.startsWith("Users"), title);
    }

    @Test
    public void testImpossiblyToCreateNewUserWithEmptyFields() {
        String title = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUser()
                .clickCreateUserButton()
                .getValidationNessage();

        Assert.assertEquals(title, "\"\" is prohibited as a username for security reasons.");
    }

    @Test
    public void testCreateNewUser() {
        String userName = new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage()
                .clickCreateUser()
                .fillFormByValidDataToCreateUser()
                .getCreatedUserName();

        Assert.assertEquals(userName, "Ivan Petrov");
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
    public void testRedirectionToManage() {
        String urlBeforeRedirection = getDriver().getCurrentUrl();

        getDriver().findElement(By.xpath("//div[@id='tasks']//a[@href='/manage']")).click();
        String currentURL = getDriver().getCurrentUrl();

        Assert.assertEquals(currentURL, urlBeforeRedirection + "manage/");
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
        List<String> itemsNames = new HomePage(getDriver())
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

    @Test
    public void testSystemButton() {
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//a[@href='configure']")).click();

        String currentUrl = getDriver().getCurrentUrl();
        String getSystemText = getDriver().findElement(By.xpath("//div[@id='main-panel']")).getText().toLowerCase();

        Assert.assertEquals(currentUrl, ProjectUtils.getUrl() + "manage/configure",
                "Current URL does not meet requirement. ");
        Assert.assertTrue(getSystemText.contains("system"), "Current page does not contain the word 'System'");
    }

    @Test
    public void testSystemMessagePreview() {
        getDriver().findElement(By.xpath("//a[contains(@href, '/manage')]")).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'configureSecurity')]"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//option[text() = 'Plain text']/..")));

        new Select(getDriver().findElement(By.xpath("//option[text() = 'Plain text']/..")))
                .selectByVisibleText("Safe HTML");

        getDriver().findElement(By.name("Apply")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[contains(@href, 'configure')])[1]"))).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@name = 'system_message']/.."))).click();
        getDriver().findElement(By.xpath("//div[@class = 'CodeMirror']/div/textarea")).sendKeys("<h2>Hello</h2>");

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@previewendpoint = '/markupFormatter/previewDescription']"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class = 'textarea-preview']/h2")).getText(), "Hello");
    }

    @Test
    public void testVisitPageThreadDumps() {
        getDriver().findElement(By.xpath("//a[@href ='/manage']")).click();

        JavascriptExecutor scrollPage = (JavascriptExecutor) getDriver();
        scrollPage.executeScript("window.scrollBy(0,5000)");

        getDriver().findElement(By.xpath("//a[@href ='systemInfo']")).click();

        getDriver().findElement(By.xpath("//*[contains(text(),'Thread Dumps')]")).click();

        getDriver().findElement(By.xpath("//a[@href ='threadDump']")).click();

        Assert.assertEquals(getDriver().findElement
                (By.xpath("//div/h1")).getText(), "Thread Dump");
    }

    @Test
    public void testCreateNewAgent() {
        getDriver().findElement(By.xpath("//a[contains(.,'Manage Jenkins')]")).click();

        getDriver().findElement(By.xpath("//dt[.='Nodes']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-button--primary")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("NewAgent");
        getDriver().findElement(By.tagName("label")).click();
        getDriver().findElement(By.xpath("//button[@id='ok']")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement newAgent = getDriver().findElement(By.xpath("//a[.='NewAgent']"));

        Assert.assertTrue(newAgent.isDisplayed());
    }

    @Test
    public void testCreationAndDisplayNewNameOnNodesPage() {
        final String myNodeName = "My name of node";
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        getDriver().findElement(By.xpath("//dt[.='Nodes']")).click();

        getDriver().findElement(By.xpath("//a[@href='new']")).click();

        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(myNodeName);
        getDriver().findElement(By.xpath("//*[.='Permanent Agent']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        List<WebElement> nodes = getDriver().findElements(By.xpath("//a[@class='jenkins-table__link model-link inside']"));
        List<String> nodesNames = new ArrayList<>();
        for (WebElement element : nodes) {
            String text = element.getText();
            nodesNames.add(text);
        }

        Assert.assertListContainsObject(nodesNames, myNodeName, myNodeName);
    }

    @Test
    public void testThemesOnPage() {
        WebElement manageButton = getDriver().findElement(By.xpath("//a[@href='/manage']"));
        manageButton.click();
        WebElement appearanceButton = getDriver().findElement(By.xpath("//a[@href='appearance']"));
        appearanceButton.click();

        List<WebElement> selectTheme = getDriver().findElements(By.xpath("//section[@class='jenkins-section']"));

        Assert.assertEquals(selectTheme.size(), 3, "Number of elements is not equal 3");
    }

    @Test
    public void testPickDarkTheme() {
        WebElement manageButton = getDriver().findElement(By.xpath("//a[@href='/manage']"));
        manageButton.click();
        WebElement appearanceButton = getDriver().findElement(By.xpath("//a[@href='appearance']"));
        appearanceButton.click();

        WebElement changedColorArea = getDriver().findElement(By.xpath("//section[@class='jenkins-section']"));

        String initialColorTheme = changedColorArea.getCssValue("background");

        WebElement darkThemeIcon = getDriver().findElement(By.xpath("//label[@for='radio-block-0']"));
        darkThemeIcon.click();

        String changedColor = changedColorArea.getCssValue("background");

        Assert.assertEquals(changedColor, initialColorTheme, "Color of theme did not changed");
    }
}