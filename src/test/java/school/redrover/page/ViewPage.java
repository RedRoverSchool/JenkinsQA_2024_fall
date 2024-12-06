package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.swing.text.View;
import java.util.List;

public class ViewPage extends HomePage {

    public ViewPage(WebDriver driver) {
        super(driver);
    }

    public ListViewConfigPage clickEditView(String name) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/view/%s/configure']".formatted(name)))).click();

        return new ListViewConfigPage(getDriver());
    }

    public List<String> getColumnList() {
        return getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//thead//th")))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public ViewPage selectViewTypeToDelete(String viewType) {
        getDriver().findElement(By.linkText(viewType)).click();
        return this;
    }

    public ViewPage deleteView() {
        getDriver().findElement(By.xpath("//a[@class= 'task-link  confirmation-link']")).click();

        return this;
    }

    public ViewPage clickYesInPopUp(){
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//dialog[@class = 'jenkins-dialog']")));
        getDriver().findElement(By.xpath("//button[@data-id = 'ok']")).click();

        return this;
    }
}
