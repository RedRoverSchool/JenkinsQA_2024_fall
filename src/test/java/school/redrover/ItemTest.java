package school.redrover;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ItemTest extends BaseTest {
    @Test
    public void newItem() {
        String ItemElement = getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).getText();
        Assert.assertEquals(ItemElement, "New Item");
    }
    @Test
    public void testCreateNewJobExists() {
        String newJob = getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/section[1]/ul/li/a/span[1]")).getText();

        Assert.assertEquals(newJob, "Create a job");
    }
}
