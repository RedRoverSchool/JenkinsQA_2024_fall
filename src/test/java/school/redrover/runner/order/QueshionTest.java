package school.redrover.runner.order;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

    public class QueshionTest extends BaseTest {

        @Test
        public void testDescription() {

            Assert.assertEquals(
                    getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(3) > div > p"))
                            .getText(),
                    "This page is where your Jenkins jobs will be displayed." +
                            " To get started, you can set up distributed builds or start building a software project.");
        }

        @Test
        public void testQueshion() {

            getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"searchform\"]/a")))
                    .click();

            Assert.assertEquals(
                    getDriver().findElement(By.xpath("//*[@id=\"search-box\"]")).getText(),
                    "Search Box");
        }
    }


