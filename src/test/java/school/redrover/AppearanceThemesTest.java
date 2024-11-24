package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AppearanceThemesTest extends BaseTest {

    @Ignore
    @Test
    public void testThemesDark() {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='appearance']")).click();
        getDriver().findElement(By.xpath("//label[@for='radio-block-0']")).click();

        getDriver().findElement(By.cssSelector("[class='attach-previous ']")).click();
        getDriver().findElement(By.xpath("//button[@name='Apply']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("html[data-theme]")).
                getAttribute("data-theme"), "dark");
    }


    @Test
    public void testThemesDefault() {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='appearance']")).click();
        getDriver().findElement(By.xpath("//label[@for='radio-block-2']")).click();

        getDriver().findElement(By.cssSelector("[class='attach-previous ']")).click();
        getDriver().findElement(By.xpath("//button[@name='Apply']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("html[data-theme]")).
                getAttribute("data-theme"), "none");
    }


    @Test
    public void testThemesSystem() {
        getDriver().findElement(By.cssSelector("[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("[href='appearance']")).click();
        getDriver().findElement(By.xpath("//label[@for='radio-block-1']")).click();

        getDriver().findElement(By.cssSelector("[class='attach-previous ']")).click();
        getDriver().findElement(By.xpath("//button[@name='Apply']")).click();

        Assert.assertTrue(getDriver().findElement(By.cssSelector("html[data-theme]")).
                getAttribute("data-theme").contains("system"));
    }
}


