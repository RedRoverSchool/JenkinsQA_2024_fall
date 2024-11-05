package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipelineTest extends BaseTest {

    private static final String MP_NAME = "NewItem";
    private static final By NAME_INPUT = By.id("name");
    private static final By CREATE_A_JOB_BUTTON = By.cssSelector("[href='newJob']");
    private static final By MULTIBRANCH_PIPELINE_PROJECT = By.cssSelector("[class$='MultiBranchProject']");
    private static final By OK_BUTTON = By.id("ok-button");

    @Test
    public void testAddDescriptionCreatingMultibranch() {
        final String expectedDescription = "Add description";

        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("MultiBranch");
        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector("[name$='description']")).sendKeys(expectedDescription);
        getDriver().findElement(By.name("Submit")).click();

        String actualDescription = getDriver().findElement(By.id("view-message")).getText();

        Assert.assertEquals(actualDescription, expectedDescription);
    }

    @Test
    public void testDeleteItemFromStatusPage() {

        getDriver().findElement(CREATE_A_JOB_BUTTON).click();

        getDriver().findElement(NAME_INPUT).sendKeys(MP_NAME);
        getDriver().findElement(MULTIBRANCH_PIPELINE_PROJECT).click();
        getDriver().findElement(OK_BUTTON).click();

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.cssSelector("[data-message*='Delete the Multibranch Pipeline']")).click();

        getDriver().findElement(By.cssSelector("[data-id='ok']")).click();

        String dashboardText = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(dashboardText,"Welcome to Jenkins!");
    }

    @Test
    public void testRenameMultibranchViaSideBar () {

        getDriver().findElement(By.cssSelector("[href='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("Hilton");
        getDriver().findElement(By.cssSelector("[class*='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[7]")).click();

        getDriver().findElement(By.cssSelector("[class*='input validated']")).clear();
        getDriver().findElement(By.cssSelector("[class*='input validated']")).sendKeys("Hilton Hotels");
        getDriver().findElement(By.cssSelector("[class*='submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(),"Hilton Hotels");
    }

    @Test
    public void testTryCreateProjectExistName() {
        final String projectName = "MultiBuild";
        final String errorMessage = "» A job already exists with the name " + "‘" + projectName + "’";

        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        JavascriptExecutor js1 = (JavascriptExecutor) getDriver();
        js1.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins-home-link']")).click();

        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();
        JavascriptExecutor js2 = (JavascriptExecutor) getDriver();
        js2.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);

        String actualMessage = getDriver().findElement(By.xpath("//*[@id='itemname-invalid']")).getText();
        Assert.assertEquals(actualMessage, errorMessage);
    }

    @Test
    public void testSelectingTriggersScanPeriodFromConfigPage() throws InterruptedException {

        getDriver().findElement(CREATE_A_JOB_BUTTON).click();

        getDriver().findElement(NAME_INPUT).sendKeys(MP_NAME);
        getDriver().findElement(MULTIBRANCH_PIPELINE_PROJECT).click();
        getDriver().findElement(OK_BUTTON).click();

        getDriver().findElement(By.cssSelector("[data-section-id='scan-multibranch-pipeline-triggers']")).click();
        Thread.sleep(500);
        getDriver().findElement(By.cssSelector("[class='jenkins-checkbox']")).click();
        Select select = new Select(getDriver().findElement(By.cssSelector("[name*='interval']")));
        select.selectByValue("12h");

        WebElement selectedValue = getDriver().findElement(By.cssSelector("[value='12h']"));
        Assert.assertTrue(selectedValue.isSelected());
    }
}
