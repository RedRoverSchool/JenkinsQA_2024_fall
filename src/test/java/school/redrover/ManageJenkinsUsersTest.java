package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class ManageJenkinsUsersTest extends BaseTest {

    @Test
    public void testOpenUsersInManege(){
        new HomePage(getDriver())
                .openManageJenkinsPage()
                .openUsersPage();

        String title = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content']/h1")).getText();

        Assert.assertTrue(title.startsWith("Users"), title);
    }
}
