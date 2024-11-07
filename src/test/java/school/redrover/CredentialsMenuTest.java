package school.redrover;

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

import static org.testng.Assert.assertTrue;

public class CredentialsMenuTest extends BaseTest {

    public void getCredentialsPage() {

        getDriver().findElement(By.className("model-link")).click();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/user/admin/credentials']")))
                .click();
    }

    public WebElement moveToUserAdmin() {

        WebElement userAdmin = getDriver().findElement(By.cssSelector(".model-link.inside.jenkins-table__link"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(userAdmin).perform();

        return userAdmin;
    }


    @Test
    public void testNavigateCredentialsMenu() {

        getCredentialsPage();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Credentials");
    }

    @Test
    public void testAddDomainArrow() {

        getCredentialsPage();
        moveToUserAdmin();

        WebElement userAdmin = moveToUserAdmin();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String arrowContent = (String) js.executeScript("return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');", userAdmin);

        assertTrue(!arrowContent.equals("none") && !arrowContent.isEmpty());

    }

    @Test
    public void testisDisplayedDomainElementDropdown() {

        getCredentialsPage();
        moveToUserAdmin();

        WebElement userAdmin = moveToUserAdmin();

        int elementWidth = userAdmin.getSize().getWidth();
        int elementHeight = userAdmin.getSize().getHeight();


        int xOffset = elementWidth - 8;
        int yOffset = elementHeight / 2;

        new Actions(getDriver()).moveToElement(userAdmin, xOffset, yOffset).click().perform();

        WebElement addDomainElement = new WebDriverWait(getDriver(), Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-dropdown")));

        assertTrue(addDomainElement.isDisplayed());

    }

}