package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class FooterTest extends BaseTest {

    private static final String EXPECTED_JENKINS_VERSION = "Jenkins 2.462.3";
    private static final String ABOUT_JENKINS_LABEL = "About Jenkins";

    @Test
    public void testJenkinsVersionOnDashboard() {
        String currentJenkinsVersion = new HomePage(getDriver())
                .getJenkinsVersion();

        Assert.assertEquals(currentJenkinsVersion, EXPECTED_JENKINS_VERSION);
    }

    @Test
    public void testJenkinsLabelInDropdown() {
        String actualButtonLabel = new HomePage(getDriver())
                .clickJenkinsVersionButton()
                .getAboutJenkinsDropdownLabelText();

        Assert.assertEquals(actualButtonLabel, ABOUT_JENKINS_LABEL);
    }

    @Test
    public void checkVersionMainPage() {
        Assert.assertEquals(getDriver().findElement(By.xpath("//button[@type='button']")).
                getText(), "Jenkins 2.462.3");
    }

    @Test
    public void testVerifyTitleDescription() {
        getDriver().findElement(By.xpath("//button[@type='button']")).click();
        getDriver().findElement(By.xpath("//*[@id='tippy-1']/div/div/div/a[1]")).click();

        getDriver().findElement(By.xpath("//*[@id='main-panel']/p")).getText();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='main-panel']/p")).getText(),
                "The leading open source automation server which enables " +
                        "developers around the world to reliably build, test, and deploy their software.");
    }

    @Test
    public void testCheckNumberOfMavenDependencies() {
        WebElement toAboutJenkins = getDriver().findElement(By.xpath("//button[@type='button']"));
        toAboutJenkins.click();

        WebElement fromDropDownMenuToAboutJenkins = getDriver().findElement(By.xpath("//a[@href='/manage/about']"));
        fromDropDownMenuToAboutJenkins.click();

        int count = getDriver().findElements(By.xpath("//*[@id='main-panel']/div[4]/table/tbody/tr")).size();

        Assert.assertEquals(count, 88);
    }
}