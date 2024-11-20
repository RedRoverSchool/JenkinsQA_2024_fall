package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class HomePageTest extends BaseTest {

        private String actualUrlAfterClickElement(By locator) {

            WebElement element = getDriver().findElement(locator);
            element.click();

            return getDriver().getCurrentUrl();
        }

        @Test
        public void testRedirectToNewItemPage() {

            Assert.assertEquals(
                    actualUrlAfterClickElement(By.xpath("//a[@href='/view/all/newJob']")),
                    "http://localhost:8080/view/all/newJob");

        }
        @Test
        public void testRedirectToBuildHistoryPage() {

            Assert.assertEquals(
                    actualUrlAfterClickElement(By.xpath("//a[@href='/view/all/builds']")),
                    "http://localhost:8080/view/all/builds");
        }

        @Test
        public void testRedirectToManageJenkinsPage() {

            Assert.assertEquals(
                    actualUrlAfterClickElement(By.xpath("//a[@href='/manage']")),
                    "http://localhost:8080/manage/");
        }

        @Test
        public void testRedirectToMyViewsPage() {

            Assert.assertEquals(
                    actualUrlAfterClickElement(By.xpath("//a[@href='/me/my-views']")),
                    "http://localhost:8080/me/my-views/view/all/");
        }

    }



