package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

    public class QueshionTest extends BaseTest {

        @Test
        public void testDescription() {

            String descriptionStr = getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(3) > div > p")).getText();
            Assert.assertEquals(descriptionStr, "This page is where your Jenkins jobs will be displayed. To get started, you can set up distributed builds or start building a software project.");
        }

        @Test
        public void testQueshion() throws InterruptedException{

            WebElement search = getDriver().findElement(By.xpath("//*[@id=\"searchform\"]/a"));
            search.click();
            Thread.sleep(300);
            String QueshionStr = getDriver().findElement(By.xpath("//*[@id=\"search-box\"]")).getText();
            Assert.assertEquals(QueshionStr, "Search Box");
        }

    }


