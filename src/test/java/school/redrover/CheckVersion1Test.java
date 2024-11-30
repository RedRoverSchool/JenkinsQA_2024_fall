package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CheckVersion1Test extends BaseTest {

    public WebElement buttonJenkins(){
        return getDriver().findElement(By.xpath("//button[@type='button']"));
    }

    @Test
    public void checkVersionMainPage(){
        Assert.assertEquals(buttonJenkins().
                getText(),"Jenkins 2.462.3");
    }

    @Ignore
    @Test
    public void checkVersionAboutJenkins(){
        buttonJenkins().click();
        getDriver().findElement(By.xpath("//*[@href='/manage/about']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[@class='app-about-heading']"))
                .getText().equals("Jenkins") &&
                getDriver().findElement(By.xpath("//p[@class='app-about-version']"))
                        .getText().equals("Version 2.462.3"));
    }
}
