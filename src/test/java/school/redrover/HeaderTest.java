package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class HeaderTest extends BaseTest {

    @Test
    public void testFullNameHelperText() {
        String fullNameInputTip = new HomePage(getDriver())
                .clickAdmin()
                .clickConfigureSidebar()
                .clickFullNameTooltip()
                .getFullNameHelperInputText();

        Assert.assertTrue(fullNameInputTip.contains(
                "Specify your name in a more human-friendly format, so that people can see your real name as opposed to your ID."));
    }

    @Test
    public void testLogOut() {
        String signInTitle = new HomePage(getDriver())
                .clickLogOut()
                .getSignInTitle();

        Assert.assertEquals(signInTitle, "Sign in to Jenkins");
    }

    @Test
    public void testGetStatusIDDescription() {
        String adminDescription = new HomePage(getDriver())
                .openAdminDropdownMenu()
                .clickConfigureAdminDropdownMenu()
                .clickStatusSidebar()
                .getUserIDText();

        Assert.assertEquals(adminDescription, "Jenkins User ID: admin");
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
}

