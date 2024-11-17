package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FooterJenkinsTest extends BaseTest{

    private static final String VERSION = "2.462.3";

        @Test
        public void testCheckVersion(){

            String version = getDriver()
                    .findElement(By.cssSelector(".jenkins-button.jenkins-button--tertiary.jenkins_ver"))
                    .getText();

            Assert.assertTrue(version.contains(VERSION), "Must be version 2.462.3");
        }
        @Test
        public void testDropdownMenu() {

            WebElement button = getDriver()
                    .findElement(By.cssSelector(".jenkins-button.jenkins-button--tertiary.jenkins_ver"));
            button.click();

            WebElement dropdownMenu = getDriver().findElement(By.cssSelector(".jenkins-dropdown"));

            List<WebElement> dropdownItems = dropdownMenu.findElements(By.cssSelector(".jenkins-dropdown__item"));

            Assert.assertEquals(dropdownItems.size(), 3);

        }

        @Test
        public void testMenuDisappearing() {

            WebElement button = getDriver()
                    .findElement(By.cssSelector(".jenkins-button.jenkins-button--tertiary.jenkins_ver"));

            button.click();

            WebElement dropdownMenu = getDriver()
                    .findElement(By.cssSelector(".jenkins-dropdown"));

            button.click();

            getWait2().until(ExpectedConditions.stalenessOf(dropdownMenu));

            List <WebElement> dropdownItems = getDriver().findElements(By.cssSelector(".jenkins-dropdown__item"));

            Assert.assertTrue(dropdownItems.isEmpty(), "The dropdown menu did not close, error");

        }

}

