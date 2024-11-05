package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AdminFullNameFieldHelperTest extends BaseTest {

    @Test
    public void testFullNameHelperText(){
        getDriver().findElement(By.xpath("//*[@href='/user/admin']")).click();
        getDriver().findElement(By.xpath("//*[@href='/user/admin/configure']")).click();
        getDriver().findElement(By.xpath("//a[@title='Help for feature: Full Name']")).click();

        String fullNameFieldHelperText = getDriver().findElement(By.xpath("//*[@class='help']/div")).getText();
        Assert.assertEquals(fullNameFieldHelperText, "Specify your name in a more human-friendly format, so that people can see your real name as opposed to your ID. For example, \"Jane Doe\" is usually easier for people to understand than IDs like \"jd513\".");
    }
}
