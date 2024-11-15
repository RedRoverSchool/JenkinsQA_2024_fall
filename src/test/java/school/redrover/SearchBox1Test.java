package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class SearchBox1Test extends BaseTest {

    private static final String ITEM_NAME = "Item Name";

    @Test
    public void testSearchMenuItem() {

        WebElement searchBox = getDriver().findElement(By.id("search-box"));
        searchBox.click();
        searchBox.sendKeys("configure" + Keys.ENTER);

        String actualUrl = getDriver().getCurrentUrl().substring(getDriver().getCurrentUrl().indexOf("/configure"));

        Assert.assertEquals(actualUrl, "/configure");
    }

    @Test
    public void testSearchCreatedInstance() {

        getDriver().findElement(By.xpath("//span/a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//div/input[@class='jenkins-input']")).sendKeys(ITEM_NAME);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.xpath("//div/button[@type='submit']")).click();

        getDriver().findElement(By.xpath("//div/button[@class='jenkins-button jenkins-submit-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        WebElement searchBox = getDriver().findElement(By.id("search-box"));
        searchBox.click();
        searchBox.sendKeys(ITEM_NAME + Keys.ENTER);

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/h1")).getText(),
                ITEM_NAME);
    }

    @Test
    public void testSearchNotExistingInstance() {

        WebElement searchBox = getDriver().findElement(By.id("search-box"));
        searchBox.click();
        searchBox.sendKeys("hjk" + Keys.ENTER);

        Assert.assertEquals(
                getDriver().findElement(By.className("error")).getText(),
                "Nothing seems to match.");
    }
}
