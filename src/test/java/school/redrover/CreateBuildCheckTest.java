package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class CreateBuildCheckTest extends BaseTest {

        // shorten the repeating code - "click on the selected element"
    private void click(String linkXpath) {
        getDriver().findElement(By.xpath(linkXpath)).click();
    }


    @Test
    public void createBuildCheckTest() throws InterruptedException {

        // create new project
        click("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a");
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("empty freestyle project");
        click("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]");
        click("//*[@id='ok-button']");
        click("//*[@id='bottom-sticker']/div/button[1]");

        // build (run) project
        click("//*[@id='breadcrumbs']/li[1]/a");
        click("//*[@id='job_empty freestyle project']/td[3]");
        click("//*[@id='tasks']/div[4]/span/a");
        Thread.sleep(2000);

        // check status project
        click("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[1]/div[1]/a");
        click("/html/body/div[3]/div/div/div/a[2]");

        List<WebElement> elements = getDriver().findElements(By.cssSelector("#main-panel > div > div.jenkins-app-bar__content.jenkins-build-caption > svg"));
        boolean existenceElement = elements.size() > 0;
        Assert.assertTrue(existenceElement);
    }

}
