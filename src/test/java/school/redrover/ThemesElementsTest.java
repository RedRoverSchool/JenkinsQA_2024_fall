package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.awt.*;
import java.util.List;

public class ThemesElementsTest extends BaseTest {

    @Test
    public void testThemesOnPage() {

        WebElement manageButton = getDriver().findElement(By.xpath("//a[@href='/manage']"));
        manageButton.click();
        WebElement appearanceButton = getDriver().findElement(By.xpath("//a[@href='appearance']"));
        appearanceButton.click();

        List<WebElement> selectTheme = getDriver().findElements(By.xpath("//section[@class='jenkins-section']"));

        Assert.assertEquals(selectTheme.size(), 3, "Number of elements is not equal 3");
        }

        @Test
        public void testPickDarkTheme(){

        WebElement manageButton = getDriver().findElement(By.xpath("//a[@href='/manage']"));
        manageButton.click();
        WebElement appearanceButton = getDriver().findElement(By.xpath("//a[@href='appearance']"));
        appearanceButton.click();

        WebElement changedColorArea = getDriver().findElement(By.xpath("//section[@class='jenkins-section']"));

        String initialColorTheme = changedColorArea.getCssValue("background");

        WebElement darkThemeIcon = getDriver().findElement(By.xpath("//label[@for='radio-block-0']"));
        darkThemeIcon.click();

        String changedColor = changedColorArea.getCssValue("background");

            System.out.println("----->" + initialColorTheme);
            System.out.println("++++++====>" + changedColor);

        Assert.assertEquals(changedColor, initialColorTheme, "Color of theme did not changed");

        }
    }

