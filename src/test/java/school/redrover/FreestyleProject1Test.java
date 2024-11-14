package school.redrover;

import com.beust.ah.A;
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

public class FreestyleProject1Test extends BaseTest {
    private static final String NEW_FREESTYLE_PROJECT_NAME = "New freestyle project";
    private static final String DESCRIPTION = "Some description";
    private void createFreestyleProject() {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.name("name")).sendKeys(NEW_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

    }
    private void createFreestyleProjectWithDescription() {
        createFreestyleProject();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Actions actions = new Actions(getDriver());
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'" + NEW_FREESTYLE_PROJECT_NAME + "')]"));

        actions.moveToElement(element).click().perform();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();

    }
    @Test
    public void testCreateFreestyleProject() throws InterruptedException {
        createFreestyleProject();

        getDriver().findElement(By.id("jenkins-name-icon")).click();
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'" + NEW_FREESTYLE_PROJECT_NAME + "')]"));
        Assert.assertTrue(element.isDisplayed());
    }

    @Test
    public void testDeleteFreestyleProject() throws InterruptedException {
        createFreestyleProject();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Actions actions = new Actions(getDriver());
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'" + NEW_FREESTYLE_PROJECT_NAME + "')]"));

        actions.moveToElement(element).click().perform();

        getDriver().findElement((By.xpath("//*[@data-title='Delete Project']"))).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();
        String emptyDashboardHeader = getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText();

        Assert.assertEquals(emptyDashboardHeader, "Welcome to Jenkins!");
    }

    @Test
    public void testRenameFreestyleProject() throws InterruptedException {
        createFreestyleProject();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Actions actions = new Actions(getDriver());
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'" + NEW_FREESTYLE_PROJECT_NAME + "')]"));

        actions.moveToElement(element).click().perform();

        getDriver().findElement((By.xpath("//*[contains(@href,'confirm-rename')]"))).click();
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        Thread.sleep(200);

        getDriver().findElement(By
                .xpath("//input[@name='newName']")).sendKeys("Renamed freestyle project");
        getDriver().findElement(By.name("Submit")).click();

        String projectName = getDriver().findElement(By
                .xpath("//*[@class='job-index-headline page-headline']")).getText();

        Assert.assertEquals(projectName, "Renamed freestyle project");
    }

    @Test
    public void testAddFreestyleProjectDescription() {
        createFreestyleProjectWithDescription();

        String description = getDriver().findElement(By.id("description")).getText();

        Assert.assertEquals(description, DESCRIPTION);
    }

    @Test
    public void testDeleteFreestyleProjectDescription() {
        createFreestyleProjectWithDescription();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("Submit")).click();

        String description = getDriver().findElement(By.id("description")).getText();

        Assert.assertEquals(description, "");
    }

    @Test
    public void testFreestyleProjectDescriptionPreview() {
        createFreestyleProject();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Actions actions = new Actions(getDriver());
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'" + NEW_FREESTYLE_PROJECT_NAME + "')]"));

        actions.moveToElement(element).click().perform();

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.className("textarea-show-preview")).click();

        String preview = getDriver().findElement(By.className("textarea-preview")).getText();

        Assert.assertEquals(preview, DESCRIPTION);
    }

    @Test
    public void testChevronDeleteFreestyleProject() throws InterruptedException {
        createFreestyleProject();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        Actions actions = new Actions(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        WebElement projectName = getDriver().findElement(By
                .xpath("//span[contains(text(),'" + NEW_FREESTYLE_PROJECT_NAME + "')]"));
        actions
                .moveToElement(projectName)
                .perform();

        WebElement chevron = getDriver().findElement(By
                .xpath("//*[@id='job_" + NEW_FREESTYLE_PROJECT_NAME + "']/td[3]/a/button"));

        js.executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevron);
        js.executeScript("arguments[0].click();", chevron);

        wait.until(ExpectedConditions.attributeToBe(chevron, "aria-expanded", "true"));

        WebElement delete = wait.until(ExpectedConditions.visibilityOfElementLocated((By
                .xpath("//*[contains(@href,'doDelete')]"))));
        delete.click();

        getDriver().findElement(By.xpath("//button[contains(text(),'Yes')]")).click();
        String emptyDashboardHeader = getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText();
        Assert.assertEquals(emptyDashboardHeader, "Welcome to Jenkins!");
    }
}