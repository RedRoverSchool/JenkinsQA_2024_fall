package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AboutJenkinsTest extends BaseTest {

    public  void toAboutJenkinsPage(){

        WebElement toAboutJenkins = getDriver().findElement(By.xpath("//button[@type='button']"));
        toAboutJenkins.click();

        WebElement fromDropDownMenuToAboutJenkins = getDriver().findElement(By.xpath("//a[@href='/manage/about']"));
        fromDropDownMenuToAboutJenkins.click();
    }

    @Test
    public void testCheckVersion() {

        toAboutJenkinsPage();

        String version = getDriver().findElement(By.xpath("//p[@class='app-about-version']")).getText();

        Assert.assertEquals(version, "Version 2.440.2");
    }

    @Test
    public void testCheckNumberOfMavenDependencies(){

        toAboutJenkinsPage();

        int count = getDriver().findElements(By.xpath("//*[@id='main-panel']/div[4]/table/tbody/tr")).size();

        Assert.assertEquals(93,count);

    }
}
