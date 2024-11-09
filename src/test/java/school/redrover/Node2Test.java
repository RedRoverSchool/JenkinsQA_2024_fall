package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class Node2Test extends BaseTest {

    @Test
    public void testСreationAndDisplayNewNameOnNodesPage() {
        final String myNodeName = "My name of node";
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        getDriver().findElement(By.xpath("//dt[.='Nodes']")).click();

        getDriver().findElement(By.xpath("//a[@href='new']")).click();

        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(myNodeName);
        getDriver().findElement(By.xpath("//*[.='Permanent Agent']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        List<WebElement> nodes = getDriver().findElements(By.xpath("//a[@class='jenkins-table__link model-link inside']"));
        List<String> nodesNames = new ArrayList<>();
        for (WebElement element : nodes) {
            String text = element.getText();
            nodesNames.add(text);
        }

        Assert.assertListContainsObject(nodesNames,myNodeName, "");
    }
}
