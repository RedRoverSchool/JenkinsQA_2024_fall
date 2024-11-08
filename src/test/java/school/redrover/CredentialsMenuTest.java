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

import static org.testng.Assert.*;

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

        assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Credentials");
    }

    @Test
    public void testAddDomainArrow() {

        getCredentialsPage();
        moveToUserAdmin();

        WebElement userAdmin = moveToUserAdmin();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String arrowContent = (String) js.executeScript("return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');", userAdmin);

        Assert.assertTrue(!arrowContent.equals("none") && !arrowContent.isEmpty());

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

        /*WebElement addDomainElement = new WebDriverWait(getDriver(), Duration.ofSeconds(10), Duration.ofMillis(200))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tippy-7']//a")));
        addDomainElement.click();*/




        try {
            WebElement addDomainElement = new WebDriverWait(getDriver(), Duration.ofSeconds(10), Duration.ofMillis(200))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='tippy-7']//a")));
            addDomainElement.click();
        } catch (Exception e) {
            // Резервный клик через JavaScript, если обычный клик не сработал
            WebElement addDomainElement = getDriver().findElement(By.xpath("//*[@id='tippy-7']//a"));
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", addDomainElement);
        }





        String newDomain = getDriver().findElement(By.xpath("//h1[text()='New domain']")).getText();

        Assert.assertEquals(newDomain, "New domain" );

    }
}