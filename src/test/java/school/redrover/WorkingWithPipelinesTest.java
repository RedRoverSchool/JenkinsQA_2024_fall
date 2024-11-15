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

        final String namePipeLine = "Regression";

        createItemUtils(namePipeLine,ItemType.PIPELINE);

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(),
                namePipeLine, "Пайплайн не найден на главной странице или текст не совпадает");

        System.out.println("Пайплайн создан и находится в списке на главной странице");
    }

    @Test
    public void testSearchFreestyleProjectOnMainPage() {

        final  String nameFreestyleProject = "Freestyle";

        createItemUtils(nameFreestyleProject,ItemType.FREESTYLE_PROJECT);

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(),
                nameFreestyleProject, "Freestyle Project не найден на главной странице или текст не совпадает");

        System.out.println("Freestyle Project создан и находится в списке на главной странице");
    }

    @Test
        public void testRenameViaChevron() {

        final String namePipeLine = "Regression";

        createItemUtils(namePipeLine, ItemType.PIPELINE);
        returnToHomePage();

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

        new Actions(getDriver()).doubleClick(inputRename).sendKeys("NewRegression").perform();

        getDriver().findElement(By.xpath("//button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']"))
                .click();

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(),
                "NewRegression", "Пайплайн не найден на главной странице или текст не совпадает");
    }
}

