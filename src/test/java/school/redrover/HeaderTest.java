package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class HeaderTest extends BaseTest {

    private void getCredentialsPage() {

        WebElement clickAdmin = getDriver().findElement(By.xpath("//a[@href='/user/admin']//button[@class='jenkins-menu-dropdown-chevron' ]"));

        new Actions(getDriver())
                .moveToElement(clickAdmin)
                .click()
                .perform();


        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/user/admin/credentials']")))
                .click();

    }

    @Test
    public void testNavigateCredentialsMenu(){

        getCredentialsPage();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Credentials");
    }


    @Test
    public void testAddDomainArrow() {

        getCredentialsPage();

        WebElement userAdmin = getDriver().findElement(By.cssSelector(".model-link.inside.jenkins-table__link"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(userAdmin).perform();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String arrowContent = (String) js.executeScript("return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');", userAdmin);

        assertTrue(!arrowContent.equals("none") && !arrowContent.isEmpty());

    }

    @Test
    public void testisDisplayedDomainElementDropdown() {

        getCredentialsPage();

        WebElement element = getDriver().findElement(By.cssSelector(".model-link.inside.jenkins-table__link"));
        new Actions(getDriver()).moveToElement(element).perform();

        WebElement element1 = getDriver().findElement(By.xpath(
                "//a[@href='/user/admin/credentials/store/user']/button"));

        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].click();", element1);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(50));
        WebElement element2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'tippy-box')]")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element2);

        assertTrue(element2.isDisplayed());

    }

    @Test
    public void testJenkinsLogoExist() {
        WebElement logo = getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']"));
        assertTrue(logo.isDisplayed(), "Jenkins logo is not displayed in the header.");

    }

    @Test
    public void testDemensionLogo() {
        WebElement logo = getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']"));
        Dimension logoSize = logo.getSize();
        assertTrue(logoSize.getWidth() > 0 && logoSize.getHeight() > 0, "Logo should have proper dimensions");

    }

    @Test
    public void testFullNameHelperText(){
        getDriver().findElement(By.xpath("//*[@href='/user/admin']")).click();
        getDriver().findElement(By.xpath("//*[@href='/user/admin/configure']")).click();
        getDriver().findElement(By.xpath("//a[@title='Help for feature: Full Name']")).click();

        String fullNameFieldHelperText = getDriver().findElement(By.xpath("//*[@class='help']/div")).getText();
        Assert.assertEquals(fullNameFieldHelperText, "Specify your name in a more human-friendly format, so that people can see your real name as opposed to your ID. For example, \"Jane Doe\" is usually easier for people to understand than IDs like \"jd513\".");
    }

    @Test
    public void testLinkLogOut() {
        WebElement linkLogOut = getDriver().findElement(By.cssSelector("a[href^='/logout']"));

        Assert.assertNotNull(linkLogOut);
    }

    @Test
    public void testGetStatusPage() {
        Actions actions = new Actions(getDriver());

        WebElement dropdown = getDriver().findElement(By.xpath("(//button[@class='jenkins-menu-dropdown-chevron'])[1]"));
        actions.moveToElement(dropdown).click().perform();

        getWait5().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(@href,'/user/admin/configure')]")
                )).click();

        getDriver().findElement(By.xpath("//span[contains(@class,'task-link-wrapper')]/a[@href='/user/admin/']")).click();

        String status = getDriver().findElement(By.xpath("//div[@id='main-panel']/div[3]")).getText();

        Assert.assertEquals(status, "Jenkins User ID: admin");
    }
}

