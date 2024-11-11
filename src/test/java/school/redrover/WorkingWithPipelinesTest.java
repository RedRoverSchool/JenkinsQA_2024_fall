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

public class WorkingWithPipelinesTest extends BaseTest {

    private void createItemUtils(String name, String locator) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.cssSelector(locator)).click();
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

        createItemUtils(namePipeLine, ".org_jenkinsci_plugins_workflow_job_WorkflowJob");

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(),
                namePipeLine, "Пайплайн не найден на главной странице или текст не совпадает");

        System.out.println("Пайплайн создан и находится в списке на главной странице");
    }

    @Test
    public void testSearchFreestyleProjectOnMainPage() {

        final  String nameFreestyleProject = "Freestyle";

        createItemUtils(nameFreestyleProject,".hudson_model_FreeStyleProject");

        returnToHomePage();

        Assert.assertListContainsObject(getProjectList(),
                nameFreestyleProject, "Freestyle Project не найден на главной странице или текст не совпадает");

        System.out.println("Freestyle Project создан и находится в списке на главной странице");
    }

    @Test
    public void OpenRenameViaDropDown() {
        final String namePipeLine = "Regression";

        createItemUtils(namePipeLine, ".org_jenkinsci_plugins_workflow_job_WorkflowJob");

        returnToHomePage();

        WebElement regressionLink = getDriver().findElement(By.xpath("//a[@href='job/Regression/']"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(regressionLink)
                .pause(1000)
                .moveToElement(getDriver().findElement(
                        By.xpath("//button[@class='jenkins-menu-dropdown-chevron' and @data-href='http://localhost:8080/job/Regression/']")))
                .click()
                .perform();

        WebDriverWait wait = new WebDriverWait(getDriver(),Duration.ofSeconds(10));
        WebElement renameOptionButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@class='jenkins-dropdown__item ' and @href='/job/Regression/confirm-rename']")));
        renameOptionButton.click();

        WebElement inputElement = getDriver().findElement(By.name("newName"));
        String value = inputElement.getAttribute("value");

        Assert.assertEquals(namePipeLine, value);
    }
}
