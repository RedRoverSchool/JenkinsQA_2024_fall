package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class DropdownMenu1Test extends BaseTest {
    @Ignore
    @Test
    public void testBreadcrumbsItemName() {
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.id("name")).sendKeys("NewFolder");
        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-nested-projects\"]/ul/li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[4]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"breadcrumbs\"]/li[8]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"tippy-7\"]/div/div/div/a")).getText(), "NewFolder");
    }
}
