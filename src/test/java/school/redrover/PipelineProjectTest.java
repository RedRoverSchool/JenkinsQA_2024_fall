package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


public class PipelineProjectTest extends BaseTest {

    private static final String PIPELINE_NAME = "Pipeline_name";
    private static final String NEW_PROJECT_NAME = "New_Pipeline_name";

    private void createProjectViaSidebar(String projectName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void returnToHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void clickJobByName(String projectName) {
        getDriver().findElement(By.xpath("//td/a[@href='job/" + projectName + "/']")).click();
    }

    private List<String> getProjectList() {
        List<WebElement> jobList = getDriver().findElements(By.xpath("//td/a[contains(@href,'job/')]"));

        return jobList
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private void clickGreenTriangleToScheduleBuildForProject(String projectName) {
        getWait(getDriver()).until(ExpectedConditions.elementToBeClickable(
                By.xpath("//td[@class='jenkins-table__cell--tight']//a[@tooltip='Schedule a Build for " + projectName + "']"))).click();

        getWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tippy-content']")));
    }

    private static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void testCreateProjectWithValidNameViaSidebar() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(), PIPELINE_NAME, "Project is not found");
    }

    @Test
    public void testVerifySidebarOptionsOnProjectPage() {
        final List<String> expectedSidebarOptionList =
                List.of("Status", "Changes", "Build Now", "Configure", "Delete Pipeline", "Stages", "Rename", "Pipeline Syntax");

        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickJobByName(PIPELINE_NAME);

        List<WebElement> sidebarList = getWait(getDriver()).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='task ']//span[2]")));
        List<String> actualSideBarOptionList = sidebarList.stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualSideBarOptionList, expectedSidebarOptionList, "Sidebar options on Project page do not match expected list.");
    }

    @Test
    public void testVerifySidebarOptionsOnConfigurationPage() {
        final List<String> expectedSidebarOptionList =
                List.of("General", "Advanced Project Options", "Pipeline");

        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickJobByName(PIPELINE_NAME);

        getWait(getDriver()).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/configure']"))).click();

        List<WebElement> sidebarLst = getWait(getDriver()).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='task']//span[2]")));
        List<String> actualSideBarOptionList = sidebarLst.stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualSideBarOptionList, expectedSidebarOptionList, "Sidebar options on Configuration page do not match expected list.");
    }

    @Test
    public void testAddDescriptionToProject() {
        final String expectedProjectDescription = "Certain project description";

        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickJobByName(PIPELINE_NAME);

        getDriver().findElement(By.id("description-link")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys(expectedProjectDescription);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualDescription = getDriver().findElement(By.id("description")).getText();

        Assert.assertEquals(actualDescription, expectedProjectDescription, "Expected description for the project is not found");
    }

    @Test
    public void testGetWarningMessageWhenDisableProject() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickJobByName(PIPELINE_NAME);

        getWait(getDriver()).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/configure']"))).click();
        getWait(getDriver()).until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@data-title='Disabled']"))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualWarningMessage = getWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//form[@id='enable-project']"))).getText().split("\n")[0];
        String buttonText = getWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//form[@id='enable-project']/button[@name='Submit']"))).getText();

        Assert.assertEquals(actualWarningMessage, "This project is currently disabled");
        Assert.assertEquals(buttonText, "Enable");
    }

    @Test
    public void testDisableProject() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickJobByName(PIPELINE_NAME);

        getWait(getDriver()).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/configure']"))).click();
        getWait(getDriver()).until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@data-title='Disabled']"))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        returnToHomePage();
        WebElement disableCircleSign = getWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tr[@id='job_Pipeline_name']//*[@tooltip='Disabled']")));

        boolean isGreenScheduleBuildTrianglePresent = !getDriver().findElements(
                        By.xpath("//td[@class='jenkins-table__cell--tight']//a[@tooltip='Schedule a Build for " + PIPELINE_NAME + "']"))
                .isEmpty();

        Assert.assertTrue(disableCircleSign.isDisplayed());
        Assert.assertFalse(isGreenScheduleBuildTrianglePresent);
    }

    @Test
    public void testGetSuccessTooltipDisplayedWhenHoverOverGreenMark() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickGreenTriangleToScheduleBuildForProject(PIPELINE_NAME);
        clickJobByName(PIPELINE_NAME);

        new Actions(getDriver())
                .moveToElement(getWait(getDriver()).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@title='Success']"))))
                .perform();

        String greenMarkTooltip = getWait(
                getDriver()).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='tippy-content']"))).getText();

        Assert.assertEquals(greenMarkTooltip, "Success");
    }

    @Test
    public void testGetPermalinksInformationUponSuccessfulBuild() {
        final List<String> expectedPermalinkList = List.of(
                "Last build",
                "Last stable build",
                "Last successful build",
                "Last completed build");

        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickGreenTriangleToScheduleBuildForProject(PIPELINE_NAME);
        clickJobByName(PIPELINE_NAME);

        List<WebElement> lastBuildInfo = getWait(getDriver()).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//li[@class='permalink-item']")));
        List<String> actualPermalinkList = lastBuildInfo.stream().map(WebElement::getText).map(string -> string.split("\\(#")[0].trim()).toList();

        Assert.assertTrue(actualPermalinkList.containsAll(expectedPermalinkList), "Not all expected permalinks are present in the actual permalinks list.");
    }

    @Test
    public void testVerifyCheckboxTooltipsContainCorrectText() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.xpath("//li[@class='org_jenkinsci_plugins_workflow_job_WorkflowJob']")).click();
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        List<WebElement> checkboxWithQuestionMarkList = getDriver().findElements(By.xpath("//div[@hashelp = 'true']//label[@class='attach-previous ']"));
        List<WebElement> questionMarkList = getDriver().findElements(By.xpath("//div[@hashelp = 'true']//a[@class='jenkins-help-button']"));

        Map<String, String> labelToTooltipTextMap = new HashMap<>();
        for (int i = 0; i < checkboxWithQuestionMarkList.size(); i++) {
            String checkboxText = checkboxWithQuestionMarkList.get(i).getText();
            String tooltipText = questionMarkList.get(i).getAttribute("tooltip");
            labelToTooltipTextMap.put(checkboxText, tooltipText);
        }

        labelToTooltipTextMap.forEach((checkbox, tooltip) ->
                Assert.assertTrue(tooltip.contains("Help for feature: " + checkbox),
                        "Tooltip for feature '" + checkbox + "' does not contain the correct text"));
    }

    @Test
    public void testKeepBuildForever() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickGreenTriangleToScheduleBuildForProject(PIPELINE_NAME);
        clickJobByName(PIPELINE_NAME);

        getWait(getDriver()).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Success']"))).click();
        getWait(getDriver()).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();

        List<WebElement> sidebarTaskList = getWait(getDriver()).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@id='tasks']/div")));

        boolean isDeleteBuildOptionPresent = sidebarTaskList.stream()
                .anyMatch(element -> element.getAttribute("href") != null &&
                        element.getAttribute("href").contains("/job/" + PIPELINE_NAME + "/1/confirmDelete"));

        Assert.assertFalse(isDeleteBuildOptionPresent, "Delete build sidebar option is displayed, but it should not be.");
    }

    @Test
    public void testRenameProjectViaSidebar() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickJobByName(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@href='/job/" + PIPELINE_NAME + "/confirm-rename']")).click();

        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(), NEW_PROJECT_NAME, "Project is not renamed");
    }

    @Test
    public void testRenameProjectViaDropdownMenu() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        Actions actions = new Actions(getDriver());

        WebElement projectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='job/" + PIPELINE_NAME + "/']")));

        int elementWidth = projectElement.getSize().getWidth();
        int xOffset = 0;
        boolean chevronClicked = false;

        while (xOffset < elementWidth && !chevronClicked) {
            actions.moveToElement(projectElement, xOffset, 0).perform();

            if (isChevronVisible()) {
                WebElement chevron = getDriver().findElement(By.cssSelector("[data-href*='/job/" + PIPELINE_NAME + "/']"));
                actions.moveToElement(chevron).pause(500).click().perform();
                chevronClicked = true;
            }

            xOffset += 5;
        }

        wait.until(ExpectedConditions.attributeToBe(projectElement.findElement(By.cssSelector("[data-href*='/job/" + PIPELINE_NAME + "/']")),
                "aria-expanded", "true"));
        wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.cssSelector(".tippy-box"))));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='tippy-content']")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='jenkins-dropdown']")));

        WebElement confirmRenameLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='jenkins-dropdown']//a[@href='/job/" + PIPELINE_NAME + "/confirm-rename']")));
        wait.until(ExpectedConditions.elementToBeClickable(confirmRenameLink));
        actions.moveToElement(confirmRenameLink).click().perform();

        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@checkdependson='newName']")));
        nameInput.clear();
        nameInput.sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(), NEW_PROJECT_NAME, "Project is not renamed");
    }

    private boolean isChevronVisible() {
        try {
            WebElement chevron = getWait(getDriver()).until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("[data-href*='/job/" + PIPELINE_NAME + "/']")));
            return chevron.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void testDeleteProjectViaSidebar() {
        createProjectViaSidebar(PIPELINE_NAME);
        returnToHomePage();
        clickJobByName(PIPELINE_NAME);

        getDriver().findElement(By.xpath("//a[@data-title='Delete Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        Assert.assertListNotContainsObject(getProjectList(), PIPELINE_NAME, "Project is not deleted");
    }
}
