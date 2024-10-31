package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PageElementsTest extends BaseTest {

    @Test
    public void newItem (){
       String newItemElement = getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).getText();
       Assert.assertEquals(newItemElement, "New Item");
    }

    @Test
    public void buildHistory (){
        String buildHistory = getDriver().findElement(By.xpath("//*[@id='tasks']/div[2]/span/a")).getText();
        Assert.assertEquals(buildHistory, "Build History");
    }

    @Test
    public void manageJenkins (){
        String manageJenkins = getDriver().findElement(By.xpath("//*[@id='tasks']/div[3]/span/a")).getText();
        Assert.assertEquals(manageJenkins, "Manage Jenkins");
    }

    @Test
    public void myView (){
        String myView = getDriver().findElement(By.xpath("//*[@id='tasks']/div[4]/span/a")).getText();
        Assert.assertEquals(myView, "My Views");
    }

    @Test
    public void addDescription (){
        String addDescription = getDriver().findElement(By.xpath("//*[@id='description-link']")).getText();
        Assert.assertEquals(addDescription, "Add description");
    }

}
