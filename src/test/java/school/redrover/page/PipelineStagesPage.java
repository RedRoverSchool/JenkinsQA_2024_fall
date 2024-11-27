package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.page.base.BasePage;

import java.util.ArrayList;
import java.util.List;

public class PipelineStagesPage extends BasePage {
    public PipelineStagesPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getAllPipelineBuilds() {
        return getDriver().findElements(By.cssSelector("a[href$='/pipeline-graph/']"));
    }

    public List<String> getAllStagesNames() {
        List<WebElement> stages = getDriver().findElements(By.cssSelector("div[class^='TruncatingLabel']"));

        return stages.stream().map(el-> el.getAttribute("title")).toList();
    }

    public List<WebElement> getGreenAndRedIcons() {
        WebElement greenIcon = getDriver().findElement(By.cssSelector("g[class$='icon-blue']"));
        WebElement redIcon = getDriver().findElement(By.cssSelector("g[class$='icon-red']"));

        List<WebElement> icons = new ArrayList<>();
        icons.add(greenIcon);
        icons.add(redIcon);

        return icons;
    }
}
