package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class HomePageTest extends BaseTest {

        private String actualUrlAfterClickElement(String xpath) {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            element.click();

            return getDriver().getCurrentUrl();
        }

        @Test
        public void testRedirectToNewItemPage() {
            Assert.assertEquals(actualUrlAfterClickElement("//a[@href='/view/all/newJob']"), "http://localhost:8080/view/all/newJob");

        }
        @Test
        public void testRedirectToBuildHistoryPage() {
            Assert.assertEquals(actualUrlAfterClickElement("//a[@href='/view/all/builds']"), "http://localhost:8080/view/all/builds");
        }

        @Test
        public void testRedirectToManageJenkinsPage() {
            Assert.assertEquals(actualUrlAfterClickElement("//a[@href='/manage']"), "http://localhost:8080/manage/");
        }

        @Test
        public void testRedirectToMyViewsPage() {
            Assert.assertEquals(actualUrlAfterClickElement("//a[@href='/me/my-views']"), "http://localhost:8080/me/my-views/view/all/");
        }

    }



