package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolderTest extends BaseTest {

    @Test
    public void testOrganizationFolder() {

        Actions actions = new Actions(getDriver());

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys("Name");
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        String title = getDriver().findElement(By.id("general")).getText();
        Assert.assertEquals(title, "General");

        actions.moveToElement(getDriver().findElement(By.xpath("//span/label[@for='enable-disable-project']"))).perform();
        String tooltip = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__controls']/span")).getAttribute("tooltip");
        Assert.assertEquals(tooltip, "(No new builds within this Organization Folder will be executed until it is re-enabled)");

        String displayName = "Display Name";
        String description = "Description";
        getDriver().findElement(By.xpath("//div/input[@name='_.displayNameOrNull']")).sendKeys(displayName);
        getDriver().findElement(By.xpath("//div/textarea[@name='_.description']")).sendKeys(description);
        getDriver().findElement(By.xpath("//div/a[@class='textarea-show-preview']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div/div[@class='textarea-preview']")).getText(), description);

        getDriver().findElement(By.xpath("//div/a[@class='textarea-hide-preview']")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//div/div[@class='textarea-preview']")).getAttribute("style"), "display: none;");

        js.executeScript("window.scrollTo(0, 200);");

        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button hetero-list-add']")).click();
        getDriver().findElement(By.xpath("//div/button[@class='jenkins-dropdown__item '][2]")).click();

        actions.moveToElement(getDriver().findElement(By.xpath("//div/button[@title='Delete']"))).perform();
        String tooltipButton = getDriver().findElement(By.xpath("//div/button[@title='Delete']")).getAttribute("tooltip");
        Assert.assertEquals(tooltipButton, "Delete");
        getDriver().findElement(By.xpath("//div[@name='navigators']/div/div/button[@title='Delete']")).click();
    }
}
