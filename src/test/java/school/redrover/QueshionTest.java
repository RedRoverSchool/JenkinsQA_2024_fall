package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

import static java.time.Duration.ofSeconds;

public class QueshionTest extends BaseTest {

        @Test
                public void testDescription() {

            String descriptionStr = getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(3) > div > p"))
                    .getText();
            Assert.assertEquals(descriptionStr, "This page is where your Jenkins jobs will be displayed." +
                    " To get started, you can set up distributed builds or start building a software project.");
        }
        @Test
        public void testQueshion() throws InterruptedException {

            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"searchform\"]/a")))
                    .click();


            Assert.assertEquals(
                    getDriver().findElement(By.xpath("//*[@id=\"search-box\"]")).getText(),
                    "Search Box");

        }

    }


