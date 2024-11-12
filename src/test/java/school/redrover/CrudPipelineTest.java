package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CrudPipelineTest extends BaseTest {

    private static final By MODEL_LIST_ELEMENT = By.xpath(
            "//*[@class='login page-header__hyperlinks']/child::a/child::button");
    private static final By MODEL_LIST_ELEMENT_MY_VIEWS = By.cssSelector(
            ".jenkins-dropdown__item:nth-child(3)");
    private static final By CREATE_JOB_ELEMENT = By.className("content-block__link");
    private static final By ITEM_NAME_ELEMENT = By.className("jenkins-input");
    private static final By PIPELINE_ELEMENT = By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob");
    private static final By OK_BUTTON = By.id("ok-button");
    private static final By DESCRIPTION_ELEMENT = By.cssSelector("[name='description']");
    private static final By SUBMIT_ELEMENT = By.cssSelector("[name='Submit']");

    private static final By ACTUAL_RESULT = By.cssSelector("[class$='page-headline']");

    @Test
    public void testCreateAndReadPipeline() {
        WebElement modelList = getDriver().findElement(MODEL_LIST_ELEMENT);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", modelList);
        WebElement modelListElementMyViews = getDriver().findElement(MODEL_LIST_ELEMENT_MY_VIEWS);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", modelListElementMyViews);

        getDriver().findElement(CREATE_JOB_ELEMENT).click();
        getDriver().findElement(ITEM_NAME_ELEMENT).sendKeys("Liza");
        getDriver().findElement(PIPELINE_ELEMENT).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(DESCRIPTION_ELEMENT).click();
        getDriver().findElement(DESCRIPTION_ELEMENT).sendKeys("description");
        getDriver().findElement(SUBMIT_ELEMENT).click();

        Assert.assertEquals(getDriver().findElement(ACTUAL_RESULT).getText(),
                "Liza");

        Assert.assertEquals(getDriver().findElement(By.xpath(
                        "//*[@class='jenkins-!-margin-bottom-3']/child::div")).getText(),
                "description");
    }

    @Test
    public void testUpdatePipeline() {
        WebElement modelList = getDriver().findElement(MODEL_LIST_ELEMENT);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", modelList);
        WebElement modelListElementMyViews = getDriver().findElement(MODEL_LIST_ELEMENT_MY_VIEWS);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", modelListElementMyViews);

        getDriver().findElement(CREATE_JOB_ELEMENT).click();
        getDriver().findElement(ITEM_NAME_ELEMENT).sendKeys("Liza");
        getDriver().findElement(PIPELINE_ELEMENT).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(DESCRIPTION_ELEMENT).click();
        getDriver().findElement(DESCRIPTION_ELEMENT).sendKeys("description");
        getDriver().findElement(SUBMIT_ELEMENT).click();

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[7]")).click();
        getDriver().findElement(By.name("newName")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys("777");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(ACTUAL_RESULT).getText(), "777");
    }

    @Test
    public void testDeletePipeline() {
        WebElement modelList = getDriver().findElement(MODEL_LIST_ELEMENT);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", modelList);
        WebElement modelListElementMyViews = getDriver().findElement(MODEL_LIST_ELEMENT_MY_VIEWS);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));" +
                "arguments[0].dispatchEvent(new Event('click'));", modelListElementMyViews);

        getDriver().findElement(CREATE_JOB_ELEMENT).click();
        getDriver().findElement(ITEM_NAME_ELEMENT).sendKeys("Liza");
        getDriver().findElement(PIPELINE_ELEMENT).click();
        getDriver().findElement(OK_BUTTON).click();
        getDriver().findElement(DESCRIPTION_ELEMENT).click();
        getDriver().findElement(DESCRIPTION_ELEMENT).sendKeys("description");
        getDriver().findElement(SUBMIT_ELEMENT).click();

        getDriver().findElement(By.className("jenkins-breadcrumbs__list-item")).click();

        WebElement namePipeline = getDriver().findElement(By.xpath(
                "//*[@class='jenkins-table__link model-link inside']/span"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(namePipeline, namePipeline.getSize().width / 2,
                namePipeline.getSize().height / 2).pause(1000).perform();

        WebElement pointerElement = getDriver().findElement(By.xpath(
                "//a[@href='job/Liza/']//button[@class='jenkins-menu-dropdown-chevron']"));

        actions.moveToElement(pointerElement, pointerElement.getSize().width / 2,
                pointerElement.getSize().height / 2).click().perform();

        WebElement deletePipeline = getDriver().findElement(By.xpath(
                "//*[@class='icon-edit-delete icon-md']"));
        deletePipeline.click();

        WebElement buttonYesDeletePipeline = getDriver().findElement(By.cssSelector("[class$='jenkins-button--primary ']"));
        buttonYesDeletePipeline.click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class='empty-state-block']/h1")).getText(),
                "Welcome to Jenkins!");
    }
}