package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class VersionTest extends BaseTest {

    private static final String EXPECTED_JENKINS_VERSION = "Jenkins 2.462.3";
    private static final String ABOUT_JENKINS_LABEL = "About Jenkins";

    @Test
    public void CheckVersionTest() {

        WebElement version = getDriver().findElement(By.xpath("//button[@type='button']"));
        new Actions(getDriver())
                .scrollToElement(version)
                .perform();
        version.click();

        getDriver().findElement(By.xpath("//a[normalize-space()='About Jenkins']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.className("app-about-version"))
                .getText(), "Version 2.462.3");


    }

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
}
