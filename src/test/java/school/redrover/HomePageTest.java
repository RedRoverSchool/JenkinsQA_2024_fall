package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class HomePageTest extends BaseTest {

        private String actualUrlAfterClickElement(String xpath) throws InterruptedException {
            WebElement element = getDriver().findElement(By.xpath(xpath));
            element.click();

            Thread.sleep(500);

            return getDriver().getCurrentUrl();

        }

        @Test
        public void testRedirectToNewItemPage() throws InterruptedException {
            Assert.assertEquals(actualUrlAfterClickElement("//a[@href='/view/all/newJob']"), "http://localhost:8080/view/all/newJob");

        }
        @Test
        public void testRedirectToBuildHistoryPage() throws InterruptedException {
            Assert.assertEquals(actualUrlAfterClickElement("//a[@href='/view/all/builds']"), "http://localhost:8080/view/all/builds");
        }

        @Test
        public void testRedirectToManageJenkinsPage() throws InterruptedException {
            Assert.assertEquals(actualUrlAfterClickElement("//a[@href='/manage']"), "http://localhost:8080/manage/");
        }

        @Test
        public void testRedirectToMyViewsPage() throws InterruptedException {
            Assert.assertEquals(actualUrlAfterClickElement("//a[@href='/me/my-views']"), "http://localhost:8080/me/my-views/view/all/");
        }

    }



