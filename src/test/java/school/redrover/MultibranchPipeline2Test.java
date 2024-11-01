package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipeline2Test extends BaseTest {

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
        getDriver().findElement(By.cssSelector("#enable-project > button")).click();

        String foundText = getDriver().findElement(By.cssSelector("#disable-project > button")).getText();
        Assert.assertEquals(foundText, "Disable Multibranch Pipeline");
    }

}
