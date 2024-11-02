package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipelineTest extends BaseTest {

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

        getDriver().findElement(By.cssSelector("[href='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("NewItem");
        getDriver().findElement(By.cssSelector("[class*='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.cssSelector("[data-message*='Delete the Multibranch Pipeline ']")).click();

        getDriver().findElement(By.cssSelector("[data-id='ok']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[text()='Welcome to Jenkins!']"))
                .getText(), "Welcome to Jenkins!");
    }

    @Test
    public void testVerifyStatusToSwitchingEnableButton() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("Muiltibranch Pipeline project");

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.cssSelector("[class$='MultiBranchProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector("#toggle-switch-enable-disable-project > label")).click();

        JavascriptExecutor js1 = (JavascriptExecutor) getDriver();
        js1.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.id("description-link")).click();

        String foundText = getDriver().findElement(By.xpath("//*[@id='description']/form/div[2]/button")).getText();
        Assert.assertEquals(foundText, "Save");
    }
}
