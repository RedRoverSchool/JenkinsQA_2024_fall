package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class WorkingWithPipelinesTest extends BaseTest {

    private static final String PIPELINE_NAME = "Regression";
    private static final String PIPELINE_RENAME = "NewRegression";
    private static final String FREESTYLE_PROJECT_NAME = "Freestyle";


    private enum ItemType {
        FREESTYLE_PROJECT(".hudson_model_FreeStyleProject"),
        PIPELINE(".org_jenkinsci_plugins_workflow_job_WorkflowJob"),
        MULTI_CONFIG_PROJECT(".hudson_matrix_MatrixProject"),
        FOLDER(".com_cloudbees_hudson_plugins_folder_Folder"),
        MULTIBRANCH_PIPELINE(".org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject"),
        ORGANIZATION_FOLDER(".jenkins_branch_OrganizationFolder");

        private final String cssSelector;

        ItemType(String cssSelector) {
            this.cssSelector = cssSelector;
        }

        public String getCssSelector() {
            return cssSelector;
        }
    }

    private void createItemUtils(String itemName, ItemType itemType) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        getDriver().findElement(By.cssSelector(itemType.getCssSelector())).click();
        getDriver().findElement(By.id("ok-button")).click();
    }
    private List<String> getProjectList() {
        List<WebElement> jobList = getDriver().findElements(By.xpath("//td/a[contains(@href,'job')]"));

        return jobList
                .stream()
                .map(WebElement::getText)//как рабоатет =>> .map(element -> element.getText())
                .toList();
    }

    private void returnToHomePage(){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-home-link")))
                .click();
    }

    @Test
    public void testSearchPipelineOnMainPage() {

        createItemUtils(PIPELINE_NAME,ItemType.PIPELINE);

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(),
                PIPELINE_NAME, "Пайплайн не найден на главной странице или текст не совпадает");
    }

    @Test
    public void testSearchFreestyleProjectOnMainPage() {

        createItemUtils(FREESTYLE_PROJECT_NAME,ItemType.FREESTYLE_PROJECT);

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(),
                FREESTYLE_PROJECT_NAME, "Freestyle Project не найден на главной странице или текст не совпадает");

        System.out.println("Freestyle Project создан и находится в списке на главной странице");
    }

    @Test(dependsOnMethods = "testSearchPipelineOnMainPage")
        public void testRenameViaChevron() {

        WebElement regressionLink = getDriver().findElement(By.xpath("//a[@href='job/Regression/']"));

        new Actions(getDriver())
            .moveToElement(regressionLink).perform();

        WebElement chevron = getDriver().findElement(By.xpath("//button[@data-href='http://localhost:8080/job/Regression/']"));

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('click'));", chevron);

        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/job/Regression/confirm-rename']")))
                .click();

        WebElement inputRename = getWait10().until(ExpectedConditions.elementToBeClickable(By.name("newName")));

        new Actions(getDriver()).doubleClick(inputRename).sendKeys(PIPELINE_RENAME).perform();

        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']"))
                .click();

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(),
                PIPELINE_RENAME, "Пайплайн не найден на главной странице или текст не совпадает");
    }

    @Test(dependsOnMethods = "testRenameViaChevron")
    public void testDeleteViaChevron() {

        createItemUtils("SecondPipeline", ItemType.PIPELINE);
        returnToHomePage();

        WebElement regressionLink = getDriver().findElement(By.xpath("//a[@href='job/NewRegression/']"));

        new Actions(getDriver())
                .moveToElement(regressionLink).perform();

        WebElement chevron = getDriver().findElement(
                By.xpath("//button[@data-href='http://localhost:8080/job/NewRegression/']"));

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new Event('click'));", chevron);

        getWait5().until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@href='/job/NewRegression/doDelete']")))
                .click();

        getWait5().until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='jenkins-button jenkins-button--primary ']")))
                .click();

        returnToHomePage();

        Assert.assertListNotContainsObject(getProjectList(), PIPELINE_RENAME,
                "Пайплайн не найден на главной странице или текст не совпадает");
    }
}

